package com.taoszu.layoutmarker

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView

object MarkerHub {

    /**
     * 开始标记
     */
    fun startMark(window: Window) {
        try {
            val decorView = window.decorView.rootView as ViewGroup
            val decorContentView = ((decorView.getChildAt(0) as ViewGroup).getChildAt(1) as ViewGroup).getChildAt(0) as ViewGroup
            val contentView = decorView.findViewById<ViewGroup>(android.R.id.content)

            contentView.viewTreeObserver.addOnGlobalLayoutListener {
                val actionBarViewHeight = decorContentView.height - contentView.height
                val context = contentView.context
                val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

                val rootMarkView = when (contentView) {
                    is ConstraintLayout -> ConstraintLayout(context)
                    is LinearLayout -> LinearLayout(context)
                    else -> FrameLayout(context)
                }

                travelViewGroup(rootMarkView, contentView)
                rootMarkView.setBackgroundColor(Color.parseColor("#20ff0000"))
                windowManager.addView(rootMarkView, getMarkViewParam(actionBarViewHeight, contentView.height))
            }

        } catch (e:Exception) {
            Log.d(MarkerConstants.TAG, e.localizedMessage)
        }

    }

    /**
     * 遍历view
     */
    private fun travelViewGroup(cloneViewGroup: ViewGroup, rootViewGroup: ViewGroup) {
        val context = rootViewGroup.context
        for (i in 0 until rootViewGroup.childCount) {
            val childView = rootViewGroup.getChildAt(i)

            if (childView is ViewGroup) {
                val viewGroup = when (childView) {
                    is ConstraintLayout -> ConstraintLayout(context)
                    is LinearLayout -> LinearLayout(context)
                    else -> FrameLayout(context)
                }
                MarkCloner.cloneViewGroup(viewGroup, childView)
                travelViewGroup(viewGroup, childView)
                cloneViewGroup.addView(viewGroup)

            } else {
                val cloneView = when (childView) {
                    is TextView -> TextView(context)
                    else -> TextView(context)
                }
                MarkCloner.cloneView(cloneView, childView)
                cloneViewGroup.addView(cloneView)
            }
        }
    }

    private fun getMarkViewParam(offsetTop:Int,height: Int): WindowManager.LayoutParams {
        return WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                height,
                0,
                offsetTop,
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                PixelFormat.TRANSPARENT)
    }

}