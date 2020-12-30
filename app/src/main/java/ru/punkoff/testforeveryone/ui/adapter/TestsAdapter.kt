package ru.punkoff.testforeveryone.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.punkoff.testforeveryone.databinding.ItemTestBinding
import ru.punkoff.testforeveryone.model.Test
import ru.punkoff.testforeveryone.model.mapToColor

val DIFF_UTIL: DiffUtil.ItemCallback<Test> = object : DiffUtil.ItemCallback<Test>() {
    override fun areItemsTheSame(oldItem: Test, newItem: Test): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Test, newItem: Test): Boolean {
        return true
    }
}

class TestsAdapter : ListAdapter<Test, TestsAdapter.TestsViewHolder>(DIFF_UTIL) {

    private lateinit var listener: Listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestsViewHolder {
        return TestsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TestsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun attachListener(listener: Listener) {
        this.listener = listener
    }

    inner class TestsViewHolder(
        parent: ViewGroup, private val binding: ItemTestBinding = ItemTestBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        private val clickListener: View.OnClickListener = View.OnClickListener {
            listener.onClick(currentTest)
        }
        private lateinit var currentTest: Test

        fun bind(item: Test) {
            currentTest = item
            Log.d(javaClass.simpleName, currentTest.toString())
            with(binding) {
                title.text = item.title
                body.text = item.body
                root.setCardBackgroundColor(item.color.mapToColor(root.context))
                playBtn.setOnClickListener(clickListener)
            }
        }

    }
}