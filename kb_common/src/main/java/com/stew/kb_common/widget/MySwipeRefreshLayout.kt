package com.stew.kb_common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.stew.kb_common.util.Extension.dp2px
import kotlin.math.abs

/**
 * Created by stew on 8/23/22.
 * mail: stewforani@gmail.com
 */
class MySwipeRefreshLayout(context: Context, attr: AttributeSet) :
    SwipeRefreshLayout(context, attr) {

    var vpIsDragging: Boolean = false
    var x1 = 0f
    var y1 = 0f
    var x2 = 0f
    var y2 = 0f

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                x1 = event.x
                y1 = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                if (vpIsDragging) {
                    return false
                }
                x2 = event.x
                y2 = event.y
                if (abs(x1 - x2) > abs(y1 - y2) && y1 <= 180.dp2px()) {
                    vpIsDragging = true
                    return false
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                vpIsDragging = false
            }
        }
        return super.onInterceptTouchEvent(event)
    }

}