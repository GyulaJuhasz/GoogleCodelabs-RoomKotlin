package com.example.roomwordsample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomwordsample.BR
import com.example.roomwordsample.data.Word
import com.example.roomwordsample.databinding.WordRecyclerViewBinding

class WordListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Word>()

    inner class WordViewHolder(val binding: WordRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = WordRecyclerViewBinding.inflate(inflater, parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.binding.setVariable(BR.recyclerViewItemWord, words[position])
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = words.size

    internal fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

}