package ru.punkoff.testforeveryone.ui.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import android.widget.LinearLayout
import java.util.*

class CustomRadioGroup(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private val mCheckable = ArrayList<View>()

    override fun addView(
        child: View, index: Int,
        params: ViewGroup.LayoutParams
    ) {
        super.addView(child, index, params)
        parseChild(child)
    }

    fun parseChild(child: View) {
        if (child is Checkable) {
            mCheckable.add(child)
            child.setOnClickListener {
                for (index in mCheckable.indices) {
                    val view = mCheckable[index] as Checkable
                    view.isChecked = view === it
                }
            }
        } else if (child is ViewGroup) {
            parseChildren(child)
        }
    }

    fun parseChildren(child: ViewGroup) {
        for (i in 0 until child.childCount) {
            parseChild(child.getChildAt(i))
        }
    }
}