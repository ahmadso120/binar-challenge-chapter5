/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sopian.challenge5.utils

import android.R
import androidx.annotation.RestrictTo
import kotlin.jvm.JvmOverloads
import androidx.annotation.ColorInt
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.google.android.material.color.MaterialColors
import androidx.core.view.WindowCompat
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.view.Window
import androidx.core.graphics.ColorUtils

/**
 * A util class that helps apply edge-to-edge mode to activity/dialog windows.
 *
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object EdgeToEdgeUtils {
    private const val EDGE_TO_EDGE_BAR_ALPHA = 128
    /**
     * Applies or removes edge-to-edge mode to the provided [Window]. When edge-to-edge mode is
     * applied, the activities, or the non-floating dialogs, that host the provided window will be
     * drawn over the system bar area by default and the system bar colors will be adjusted according
     * to the background color you provide.
     *
     * @param statusBarOverlapBackgroundColor The reference background color to decide the text/icon
     * colors on status bars. `null` to use the default color from
     * `?android:attr/colorBackground`.
     * @param navigationBarOverlapBackgroundColor The reference background color to decide the icon
     * colors on navigation bars.`null` to use the default color from
     * `?android:attr/colorBackground`.
     */
    /**
     * Applies or removes edge-to-edge mode to the provided [Window]. When edge-to-edge mode is
     * applied, the activities, or the non-floating dialogs, that host the provided window will be
     * drawn over the system bar area by default and the system bar colors will be adjusted according
     * to the background color you provide.
     */
    @JvmOverloads
    fun applyEdgeToEdge(
        window: Window,
        edgeToEdgeEnabled: Boolean = true,
        @ColorInt statusBarOverlapBackgroundColor: Int? = null,
        @ColorInt navigationBarOverlapBackgroundColor: Int? = null
    ) {
        var statusBarOverlapBackgroundColor = statusBarOverlapBackgroundColor
        var navigationBarOverlapBackgroundColor = navigationBarOverlapBackgroundColor
        if (VERSION.SDK_INT < VERSION_CODES.LOLLIPOP) {
            return
        }

        // If the overlapping background color is unknown or TRANSPARENT, use the default one.
        val useDefaultBackgroundColorForStatusBar =
            statusBarOverlapBackgroundColor == null || statusBarOverlapBackgroundColor == 0
        val useDefaultBackgroundColorForNavigationBar =
            navigationBarOverlapBackgroundColor == null || navigationBarOverlapBackgroundColor == 0
        if (useDefaultBackgroundColorForStatusBar || useDefaultBackgroundColorForNavigationBar) {
            val defaultBackgroundColor =
                MaterialColors.getColor(window.context, R.attr.colorBackground, Color.BLACK)
            if (useDefaultBackgroundColorForStatusBar) {
                statusBarOverlapBackgroundColor = defaultBackgroundColor
            }
            if (useDefaultBackgroundColorForNavigationBar) {
                navigationBarOverlapBackgroundColor = defaultBackgroundColor
            }
        }
        WindowCompat.setDecorFitsSystemWindows(window, !edgeToEdgeEnabled)
        val statusBarColor = getStatusBarColor(window.context, edgeToEdgeEnabled)
        val navigationBarColor = getNavigationBarColor(window.context, edgeToEdgeEnabled)
        window.statusBarColor = statusBarColor
        window.navigationBarColor = navigationBarColor
        val isLightStatusBar = isUsingLightSystemBar(
            statusBarColor, MaterialColors.isColorLight(
                statusBarOverlapBackgroundColor!!
            )
        )
        val isLightNavigationBar = isUsingLightSystemBar(
            navigationBarColor, MaterialColors.isColorLight(navigationBarOverlapBackgroundColor!!)
        )
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        if (insetsController != null) {
            insetsController.isAppearanceLightStatusBars = isLightStatusBar
            insetsController.isAppearanceLightNavigationBars = isLightNavigationBar
        }
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    private fun getStatusBarColor(context: Context, isEdgeToEdgeEnabled: Boolean): Int {
        if (isEdgeToEdgeEnabled && VERSION.SDK_INT < VERSION_CODES.M) {
            // Light status bars are only supported on M+. So we need to use a translucent black status
            // bar instead to ensure the text/icon contrast of it.
            val opaqueStatusBarColor =
                MaterialColors.getColor(context, R.attr.statusBarColor, Color.BLACK)
            return ColorUtils.setAlphaComponent(opaqueStatusBarColor, EDGE_TO_EDGE_BAR_ALPHA)
        }
        return if (isEdgeToEdgeEnabled) {
            Color.TRANSPARENT
        } else MaterialColors.getColor(
            context,
            R.attr.statusBarColor,
            Color.BLACK
        )
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    private fun getNavigationBarColor(context: Context, isEdgeToEdgeEnabled: Boolean): Int {
        // Light navigation bars are only supported on O_MR1+. So we need to use a translucent black
        // navigation bar instead to ensure the text/icon contrast of it.
        if (isEdgeToEdgeEnabled && VERSION.SDK_INT < VERSION_CODES.O_MR1) {
            val opaqueNavBarColor =
                MaterialColors.getColor(context, R.attr.navigationBarColor, Color.BLACK)
            return ColorUtils.setAlphaComponent(opaqueNavBarColor, EDGE_TO_EDGE_BAR_ALPHA)
        }
        return if (isEdgeToEdgeEnabled) {
            Color.TRANSPARENT
        } else MaterialColors.getColor(
            context,
            R.attr.navigationBarColor,
            Color.BLACK
        )
    }

    private fun isUsingLightSystemBar(systemBarColor: Int, isLightBackground: Boolean): Boolean {
        return MaterialColors.isColorLight(systemBarColor) || systemBarColor == Color.TRANSPARENT && isLightBackground
    }
}