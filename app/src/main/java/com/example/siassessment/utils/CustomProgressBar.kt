package com.example.siassessment.utils

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.RelativeLayout
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class CustomProgressBar @Inject constructor(@ActivityContext val context: Context) {

    private var mProgressBar: ProgressBar? = null

    init {
        if (mProgressBar == null) {
            val layout =
                (context as Activity).findViewById<View>(android.R.id.content).rootView as ViewGroup

            mProgressBar = ProgressBar(context, null, android.R.attr.progressBarStyleLarge)
            mProgressBar!!.isIndeterminate = true

            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )

            val rl = RelativeLayout(context)

            rl.gravity = Gravity.CENTER
            rl.addView(mProgressBar)

            layout.addView(rl, params)

            hide()
        }
    }

    fun hide() {
        mProgressBar?.visibility = View.GONE
        /** enable touch on screen on hiding progressbar */
        (context as Activity).window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun show() {
        mProgressBar?.visibility = View.VISIBLE
        /** disable touch on screen when progressbar is visible */
        (context as Activity).window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

}