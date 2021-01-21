package ru.punkoff.testforeveryone.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.data.local.room.mapToColor
import ru.punkoff.testforeveryone.databinding.ItemTestBinding

val DIFF_UTIL: DiffUtil.ItemCallback<TestEntity> = object : DiffUtil.ItemCallback<TestEntity>() {
    override fun areItemsTheSame(oldItem: TestEntity, newItem: TestEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TestEntity, newItem: TestEntity): Boolean {
        return true
    }
}

class TestsAdapter : ListAdapter<TestEntity, TestsAdapter.TestsViewHolder>(DIFF_UTIL) {

    private lateinit var listener: Listener

    private lateinit var deleteCardListener: DeleteCardListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestsViewHolder {
        return TestsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TestsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun attachListener(listener: Listener) {
        this.listener = listener
    }

    fun attachDeleteListener(listener: DeleteCardListener) {
        deleteCardListener = listener
    }

    inner class TestsViewHolder(
        parent: ViewGroup, private val binding: ItemTestBinding = ItemTestBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        private val clickListener: View.OnClickListener = View.OnClickListener {
            listener.onClick(currentTest)
        }

        private val deleteCardClickListener: View.OnClickListener = View.OnClickListener {
            deleteCardListener.onClick(currentTest)

        }
        private lateinit var currentTest: TestEntity

        fun bind(item: TestEntity) {
            currentTest = item
            Log.d(javaClass.simpleName, currentTest.toString())
            with(binding) {
                title.text = item.title
                body.text = item.body
                dataPlay.text = item.createData
                cardViewBackground.setBackgroundResource(item.color.mapToColor())
                playBtn.setOnClickListener(clickListener)
                deleteIcon.setOnClickListener(deleteCardClickListener)
            }
        }
    }
}