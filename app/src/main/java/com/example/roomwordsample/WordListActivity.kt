package com.example.roomwordsample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.roomwordsample.adapter.WordListAdapter
import com.example.roomwordsample.data.Word
import com.example.roomwordsample.databinding.WordListActivityBinding
import com.example.roomwordsample.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.activity_word_list.*

class WordListActivity : AppCompatActivity(), WordListPresenter {
    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<WordListActivityBinding>(this, R.layout.activity_word_list)
        setSupportActionBar(toolbar)

        val adapter = WordListAdapter(this)

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        wordViewModel.allWords.observe(this, Observer { words ->
            words?.let { newWords -> adapter.setWords(newWords) }
        })

        binding.setVariable(BR.presenter, this)
        binding.setVariable(BR.wordListAdapter, adapter)
        binding.executePendingBindings()
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
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }

    override fun addNewWordAction() {
        val intent = Intent(this@WordListActivity, NewWordActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_NEW_WORD)
    }

    companion object {
        const val REQUEST_CODE_NEW_WORD = 1
    }

}
