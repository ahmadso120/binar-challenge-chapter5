package com.sopian.challenge5.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    anchor: View? = null
) {
    Snackbar.make(this, message, duration)
        .setAnchorView(anchor)
        .show()
}