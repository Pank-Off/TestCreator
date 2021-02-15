package ru.punkoff.testforeveryone.ui.all_tests.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.data.local.room.mapToColor
import ru.punkoff.testforeveryone.databinding.ItemTestBinding
import ru.punkoff.testforeveryone.ui.your_tests.adapter.Listener
import java.util.*
import kotlin.collections.ArrayList

val DIFF_UTIL: DiffUtil.ItemCallback<TestEntity> = object : DiffUtil.ItemCallback<TestEntity>() {
    override fun areItemsTheSame(oldItem: TestEntity, newItem: TestEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TestEntity, newItem: TestEntity): Boolean {
        return true
    }
}

class AllTestsAdapter : ListAdapter<TestEntity, AllTestsAdapter.TestsViewHolder>(DIFF_UTIL),
    Filterable {
    private lateinit var testsListFiltered: List<TestEntity>
    private lateinit var listener: Listener

    private var firstStart = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestsViewHolder {
        return TestsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TestsViewHolder, position: Int) {
        holder.bind(testsListFiltered[position])
    }

    fun attachListener(listener: Listener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        if (firstStart) {
            testsListFiltered = currentList
        }
        testsListFiltered = testsListFiltered.filter { currentList.contains(it) }
        return testsListFiltered.size
    }

    inner class TestsViewHolder(
        parent: ViewGroup, private val binding: ItemTestBinding = ItemTestBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        private val clickListener: View.OnClickListener = View.OnClickListener {
            listener.onClick(currentTest)
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
                deleteIcon.visibility = ImageView.GONE
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
                    testsListFiltered = currentList
                } else {
                    val filteredList: ArrayList<TestEntity> = ArrayList()
                    for (test in currentList) {
                        if (test.title.toLowerCase(Locale.ROOT)
                                .contains(charString.toLowerCase(Locale.ROOT))
                        ) {
                            Log.d(
                                javaClass.simpleName,
                                "TEST title: ${test.title.toLowerCase(Locale.ROOT)}"
                            )
                            filteredList.add(test)
                        }
                    }

                    testsListFiltered = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = testsListFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                testsListFiltered = results.values as List<TestEntity>
                firstStart = false
                notifyDataSetChanged()
            }
        }
    }
}