package com.sopian.challenge5.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.sopian.challenge5.R

class ContentLoadingLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs, 0) {

    init {
        inflate(context, R.layout.loading_state_layout, this)
    }

    var mStartTime: Long = -1
    var mPostedHide = false
    var mPostedShow = false
    var mDismissed = false

    private val mDelayedHide = Runnable {
        mPostedHide = false
        mStartTime = -1
        visibility = View.GONE
    }

    private val mDelayedShow = Runnable {
        mPostedShow = false
        if (!mDismissed) {
            mStartTime = System.currentTimeMillis()
            visibility = View.VISIBLE
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        removeCallbacks()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeCallbacks()
    }

    private fun removeCallbacks() {
        removeCallbacks(mDelayedHide)
        removeCallbacks(mDelayedShow)
    }

    @Synchronized
    fun hide() {
        mDismissed = true
        removeCallbacks(mDelayedShow)
        mPostedShow = false
        val diff = System.currentTimeMillis() - mStartTime
        if (diff >= MIN_SHOW_TIME || mStartTime == -1L) {
            visibility = View.GONE
        } else {
            if (!mPostedHide) {
                postDelayed(mDelayedHide, MIN_SHOW_TIME - diff)
                mPostedHide = true
            }
        }
    }

    @Synchronized
    fun show() {
        // Reset the start time.
        mStartTime = -1
        mDismissed = false
        removeCallbacks(mDelayedHide)
        mPostedHide = false
        if (!mPostedShow) {
            postDelayed(mDelayedShow, MIN_DELAY.toLong())
            mPostedShow = true
        }
    }

    companion object {

        private const val MIN_SHOW_TIME = 500
        private const val MIN_DELAY = 500
    }
}