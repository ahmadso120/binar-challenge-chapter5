package com.sopian.challenge5.utils

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.core.view.WindowCompat

fun enableStatusBar(isDark: Boolean, view: View, window: Window) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val controller = view.windowInsetsController
        controller?.setSystemBarsAppearance(
            if (isDark) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    } else {
        WindowCompat.getInsetsController(window, window.decorView)?.apply {
            isAppearanceLightStatusBars = isDark
        }
    }
}