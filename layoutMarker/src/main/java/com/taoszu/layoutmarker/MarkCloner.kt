package com.taoszu.layoutmarker

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

object MarkCloner {

    fun cloneViewGroup(cloneView: ViewGroup, originView: ViewGroup) {
        cloneCommon(cloneView, originView)
        when (originView) {
            is LinearLayout -> {
                (cloneView as LinearLayout).orientation = originView.orientation
            }
        }
    }

    fun cloneView(cloneView: View, originView: View) {
        cloneCommon(cloneView, originView)
        when(originView) {
            is TextView -> {
                (cloneView as TextView).text = originView.text
            }
        }
    }

    private fun cloneCommon(cloneView: View, originView: View) {
        cloneView.layoutParams = originView.layoutParams
        cloneView.id = originView.id
        cloneView.setBackgroundResource(R.drawable.mark_bg)
    }

}