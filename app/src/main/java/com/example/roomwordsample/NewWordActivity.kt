package com.example.roomwordsample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.roomwordsample.databinding.NewWordActivityBinding

class NewWordActivity : AppCompatActivity(), NewWordPresenter {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<NewWordActivityBinding>(this, R.layout.activity_new_word)
        binding.setVariable(BR.newWordPresenter, this)
        binding.executePendingBindings()
    }

    override fun saveNewWordAction(word: String) {
        val replyIntent = Intent()
        if (TextUtils.isEmpty(word)) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
        } else {
            replyIntent.putExtra(EXTRA_REPLY, word)
            setResult(Activity.RESULT_OK, replyIntent)
        }
        finish()
    }

    companion object {
        const val EXTRA_REPLY = "com.example.roomwordsample.NewWordActivity.REPLY"
    }

}
