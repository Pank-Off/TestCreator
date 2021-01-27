package ru.punkoff.testforeveryone.ui.your_tests.play_test.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.databinding.ItemFragmentTestBinding
import ru.punkoff.testforeveryone.model.Question
import kotlin.properties.Delegates

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
    var firstStart = true
    private var currentPosition = 0
    fun attachListener(listener: Listener) {
        this.listener = listener
    }

    fun showQuestion(position: Int) {
        firstStart = false
        currentPosition = position
        notifyItemChanged(currentPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.bind(getItem(position), position, currentPosition)
    }

    inner class TestViewHolder(
        parent: ViewGroup,
        private val binding: ItemFragmentTestBinding = ItemFragmentTestBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var keys: List<String>


        fun bind(item: Question, position: Int, currentPosition: Int) {
            Log.d(javaClass.simpleName, "Position in ViewHolder: $position")
            keys = item.answers.map { it.key }

            with(binding) {
                if (firstStart) {
                    question.visibility = TextView.GONE
                    buttonOne.visibility = View.GONE
                    buttonTwo.visibility = View.GONE
                    buttonThree.visibility = View.GONE
                    question.text = "${position + 1}. ${item.question}"
                } else if (currentPosition == position) {

                    question.visibility = TextView.VISIBLE
                    buttonOne.visibility = View.VISIBLE
                    buttonTwo.visibility = View.VISIBLE
                    buttonThree.visibility = View.VISIBLE

                    question.text = "${position + 1}. ${item.question}"

                    root.startAnimation(
                        AnimationUtils.loadAnimation(
                            question.context,
                            R.anim.enlarge_main_fab
                        )
                    )

                    when (keys.size) {
                        0 -> {
                            buttonOne.visibility = View.GONE
                            buttonTwo.visibility = View.GONE
                            buttonThree.visibility = View.GONE
                        }
                        1 -> {
                            buttonOne.text = keys[0]
                            buttonTwo.visibility = View.GONE
                            buttonThree.visibility = View.GONE

                        }
                        2 -> {
                            buttonThree.visibility = View.GONE

                            buttonOne.text = keys[0]
                            buttonTwo.text = keys[1]
                        }
                        3 -> {
                            buttonOne.text = keys[0]
                            buttonTwo.text = keys[1]
                            buttonThree.text = keys[2]
                        }
                    }

                    buttonOne.setOnClickListener {
                        buttonTwo.alpha = 0.5F
                        buttonThree.alpha = 0.5f
                        buttonOne.alpha = 1.0f
                        listener.onClick(item.question, item.answers[keys[0]])
                    }
                    buttonTwo.setOnClickListener {
                        buttonOne.alpha = 0.5F
                        buttonTwo.alpha = 1.0F
                        buttonThree.alpha = 0.5f
                        listener.onClick(item.question, item.answers[keys[1]])
                    }

                    buttonThree.setOnClickListener {
                        buttonTwo.alpha = 0.5F
                        buttonOne.alpha = 0.5f
                        buttonThree.alpha = 1.0f
                        listener.onClick(item.question, item.answers[keys[2]])
                    }
                }
            }

        }

    }

}