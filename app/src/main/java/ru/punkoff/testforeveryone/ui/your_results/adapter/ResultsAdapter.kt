package ru.punkoff.testforeveryone.ui.your_results.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.punkoff.testforeveryone.data.local.room.ResultEntity
import ru.punkoff.testforeveryone.data.local.room.mapToColor
import ru.punkoff.testforeveryone.databinding.ItemResultBinding


val DIFF_UTIL: DiffUtil.ItemCallback<ResultEntity> =
    object : DiffUtil.ItemCallback<ResultEntity>() {
        override fun areItemsTheSame(oldItem: ResultEntity, newItem: ResultEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ResultEntity, newItem: ResultEntity): Boolean {
            return true
        }
    }

class ResultsAdapter : ListAdapter<ResultEntity, ResultsAdapter.ResultsViewHolder>(DIFF_UTIL) {

    private lateinit var listener: Listener
    private lateinit var deleteCardResultListener: DeleteResultCardListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        return ResultsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun attachListener(listener: Listener) {
        this.listener = listener
    }

    fun attachDeleteListener(listener: DeleteResultCardListener) {
        deleteCardResultListener = listener
    }

    inner class ResultsViewHolder(
        parent: ViewGroup, private val binding: ItemResultBinding = ItemResultBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        private val clickListener: View.OnClickListener = View.OnClickListener {
            listener.onClick(currentResult)
        }
        private val deleteCardResultClickListener: View.OnClickListener = View.OnClickListener {
            deleteCardResultListener.onClick(currentResult)

        }
        private lateinit var currentResult: ResultEntity

        fun bind(item: ResultEntity) {
            currentResult = item
            Log.d(javaClass.simpleName, currentResult.toString())
            with(binding) {
                title.text = item.testTitle
                body.text = item.title
                dataPlay.text = item.lastPlayData
                cardViewBackground.setBackgroundResource(item.color.mapToColor())
                showBtn.setOnClickListener(clickListener)
                deleteIcon.setOnClickListener(deleteCardResultClickListener)
            }
        }
    }
}