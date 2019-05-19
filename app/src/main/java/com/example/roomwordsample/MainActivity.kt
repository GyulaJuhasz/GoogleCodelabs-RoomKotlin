package com.example.roomwordsample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomwordsample.adapter.WordListAdapter
import com.example.roomwordsample.data.Word
import com.example.roomwordsample.viewmodel.WordViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        wordViewModel.allWords.observe(this, Observer { words ->
            words?.let { newWords -> adapter.setWords(newWords) }
        })

        fab.setOnClickListener { view ->
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_NEW_WORD)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != REQUEST_CODE_NEW_WORD) {
            return
        }

        if (resultCode == Activity.RESULT_OK) {
            data?.let { resultIntent ->
                val word = resultIntent.getStringExtra(NewWordActivity.EXTRA_REPLY)
                wordViewModel.insert(Word(word))
            }
        } else {
            Snackbar.make(fab, R.string.empty_not_saved, Snackbar.LENGTH_LONG).show()
        }
    }

    companion object {
        const val REQUEST_CODE_NEW_WORD = 1
    }

}
