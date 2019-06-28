package com.taoszu.layoutmarker

import android.graphics.drawable.LayerDrawable
import android.util.Log
import android.view.*

object MarkerHub {

    /**
     * 开始标记
     */
    fun startMark(window: Window) {
        try {
            val decorView = window.decorView.rootView as ViewGroup
            val contentView = decorView.findViewById<ViewGroup>(android.R.id.content)
            contentView.viewTreeObserver.addOnGlobalLayoutListener(
                    object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            //todo dirty awareness
                            travelView(contentView)
                            contentView.viewTreeObserver.removeOnGlobalLayoutListener(this) }
                    }
            )
        } catch (e:Exception) {
            Log.d(MarkerConstants.TAG, e.localizedMessage)
        }
    }

    /**
     * 遍历view
     */
    private fun travelView(view: View) {
        view.applyBorder()
        if (view is ViewGroup) {
            view.forEach { travelView(it) }
        }
    }

    private fun ViewGroup.forEach(action: (child: View) -> Unit) {
        for (i in 0 until childCount) {
            action(getChildAt(i))
        }
    }

    private fun View.applyBorder() {
        if (background == null) {
            background = LayerDrawable(arrayOf(BorderDrawable(this)))
        } else {
            val oldBackground = background
            if (oldBackground is LayerDrawable) {
                for (index in 0 until oldBackground.numberOfLayers) {
                    val layer = oldBackground.getDrawable(index)
                    if (layer is BorderDrawable) { // contained
                        return
                    }
                }
            }
            this.background = LayerDrawable(arrayOf(oldBackground, BorderDrawable(this)))
        }
    }
}