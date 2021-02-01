package ru.punkoff.testforeveryone.ui.your_results.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.punkoff.testforeveryone.data.local.room.ResultEntity
import ru.punkoff.testforeveryone.data.local.room.mapToColor
import ru.punkoff.testforeveryone.databinding.ItemResultBinding
import java.util.*
import kotlin.collections.ArrayList


val DIFF_UTIL: DiffUtil.ItemCallback<ResultEntity> =
    object : DiffUtil.ItemCallback<ResultEntity>() {
        override fun areItemsTheSame(oldItem: ResultEntity, newItem: ResultEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ResultEntity, newItem: ResultEntity): Boolean {
            return true
        }
    }

class ResultsAdapter : ListAdapter<ResultEntity, ResultsAdapter.ResultsViewHolder>(DIFF_UTIL),
    Filterable {

    private lateinit var resultsListFiltered: List<ResultEntity>
    private var firstStart = true
    private var successDelete = false
    private lateinit var listener: Listener
    private lateinit var deleteCardResultListener: DeleteResultCardListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        return ResultsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        Log.d(javaClass.simpleName, "resultsListFiltered $resultsListFiltered")
        Log.d(javaClass.simpleName, "CurrentListFiltered $currentList")
        holder.bind(resultsListFiltered[position])
    }

    fun attachListener(listener: Listener) {
        this.listener = listener
    }

    fun attachDeleteListener(listener: DeleteResultCardListener) {
        deleteCardResultListener = listener
    }

    override fun getItemCount(): Int {
        if (firstStart) {
            resultsListFiltered = currentList
        }
        if (successDelete) {
            resultsListFiltered = resultsListFiltered.filter { currentList.contains(it) }
            successDelete = false
        }
        return resultsListFiltered.size
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
            successDelete = true
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

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charString: String = constraint.toString()
                Log.d(javaClass.simpleName + " charString", charString)
                Log.d(javaClass.simpleName, currentList.toString())
                if (charString.isEmpty()) {
                    resultsListFiltered = currentList
                } else {
                    val filteredList: ArrayList<ResultEntity> = ArrayList()
                    for (result in currentList) {
                        if (result.testTitle.toLowerCase(Locale.ROOT)
                                .contains(charString.toLowerCase(Locale.ROOT))
                        ) {

                            filteredList.add(result)
                        }
                    }

                    resultsListFiltered = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = resultsListFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                resultsListFiltered = results.values as List<ResultEntity>
                firstStart = false
                notifyDataSetChanged()
            }
        }
    }
}