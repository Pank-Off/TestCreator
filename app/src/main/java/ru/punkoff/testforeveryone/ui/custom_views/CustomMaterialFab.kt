package ru.punkoff.testforeveryone.ui.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.punkoff.testforeveryone.R

class CustomMaterialFab(context: Context, attrs: AttributeSet) :
    FloatingActionButton(context, attrs) {
    fun hideFabs(
        delete_fab: FloatingActionButton,
        add_fab: FloatingActionButton,
        next_step_fab: FloatingActionButton
    ) {
        val layoutParamsDeleteFab = delete_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsDeleteFab.rightMargin -= (delete_fab.width * 3.5).toInt()
        layoutParamsDeleteFab.bottomMargin -= (delete_fab.height * 0.0).toInt()
        delete_fab.layoutParams = layoutParamsDeleteFab
        delete_fab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.delete_fab_hide))
        delete_fab.isClickable = false

        val layoutParamsAddFab = add_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsAddFab.rightMargin -= (add_fab.width * 2.5).toInt()
        layoutParamsAddFab.bottomMargin -= (add_fab.height * 0.0).toInt()
        add_fab.layoutParams = layoutParamsAddFab
        add_fab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.add_fab_hide))
        add_fab.isClickable = false

        val layoutParamsNextStepFab = next_step_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsNextStepFab.rightMargin -= (next_step_fab.width * 1.5).toInt()
        layoutParamsNextStepFab.bottomMargin -= (next_step_fab.height * 0.0).toInt()
        next_step_fab.layoutParams = layoutParamsNextStepFab
        next_step_fab.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.next_step_fab_hide
            )
        )
        next_step_fab.isClickable = false
    }

    fun showFabs(
        delete_fab: FloatingActionButton,
        add_fab: FloatingActionButton,
        next_step_fab: FloatingActionButton
    ) {
        val layoutParamsDeleteFab = delete_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsDeleteFab.rightMargin += (delete_fab.width * 3.5).toInt()
        layoutParamsDeleteFab.bottomMargin += (delete_fab.height * 0.0).toInt()
        delete_fab.layoutParams = layoutParamsDeleteFab
        delete_fab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.delete_fab_show))
        delete_fab.isClickable = true

        val layoutParamsAddFab = add_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsAddFab.rightMargin += (add_fab.width * 2.5).toInt()
        layoutParamsAddFab.bottomMargin += (add_fab.height * 0.0).toInt()
        add_fab.layoutParams = layoutParamsAddFab
        add_fab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.add_fab_show))
        add_fab.isClickable = true

        val layoutParamsNextStepFab = next_step_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsNextStepFab.rightMargin += (next_step_fab.width * 1.5).toInt()
        layoutParamsNextStepFab.bottomMargin += (next_step_fab.height * 0.0).toInt()
        next_step_fab.layoutParams = layoutParamsNextStepFab
        next_step_fab.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.next_step_fab_show
            )
        )
        next_step_fab.isClickable = true
    }
}