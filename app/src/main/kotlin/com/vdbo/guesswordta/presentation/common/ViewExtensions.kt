package com.vdbo.guesswordta.presentation.common

import android.app.Activity
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat


fun View.setBackgroundColorRes(@ColorRes color: Int) {
    setBackgroundColor(ContextCompat.getColor(context, color))
}

fun Activity.displaySize() = DisplayMetrics().run {
    windowManager.defaultDisplay.getMetrics(this)
    Pair(this.widthPixels, this.heightPixels)
}