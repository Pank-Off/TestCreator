package ru.punkoff.testforeveryone.ui.your_tests.pass_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.databinding.ItemFragmentTestBinding
import ru.punkoff.testforeveryone.databinding.ItemTestBinding
import ru.punkoff.testforeveryone.model.Question

val DIFF_UTIL: DiffUtil.ItemCallback<Question> = object : DiffUtil.ItemCallback<Question>() {
    override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
        return true
    }
}

class TestAdapter : ListAdapter<Question, TestAdapter.TestViewHolder>(DIFF_UTIL) {

    private lateinit var listener: Listener

    fun attachListener(listener: Listener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class TestViewHolder(
        parent: ViewGroup,
        private val binding: ItemFragmentTestBinding = ItemFragmentTestBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

    ) : RecyclerView.ViewHolder(binding.root) {

        private val clickListener: View.OnClickListener = View.OnClickListener {
            listener.onClick()
        }

        fun bind(item: Question, position: Int) {
            with(binding) {
                question.text = "${position + 1}. ${item.question}"
                textAnswerOne.text = "A. ${item.answerOne}"
                textAnswerTwo.text = "B. ${item.answerTwo}"
                textAnswerThree.text = "C. ${item.answerThree}"
                radioBtnOne.setOnClickListener {
                    radioBtnTwo.isChecked = false
                    radioBtnThree.isChecked = false
                }
                radioBtnTwo.setOnClickListener {
                    radioBtnOne.isChecked = false
                    radioBtnThree.isChecked = false
                }

                radioBtnThree.setOnClickListener {
                    radioBtnTwo.isChecked = false
                    radioBtnOne.isChecked = false
                }
            }
        }
    }
}