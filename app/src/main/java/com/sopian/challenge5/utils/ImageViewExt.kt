package com.sopian.challenge5.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.sopian.challenge5.GlideApp

const val CROSS_FADE_DURATION = 350


fun ImageView.loadPhotoUrl(
    url: String,
    color: String? = "#6E633A",
    requestListener: RequestListener<Drawable>? = null
) {
    color?.let { background = ColorDrawable(Color.parseColor(it)) }
    GlideApp.with(context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(CROSS_FADE_DURATION))
        .addListener(requestListener)
        .into(this)
        .clearOnDetach()
}