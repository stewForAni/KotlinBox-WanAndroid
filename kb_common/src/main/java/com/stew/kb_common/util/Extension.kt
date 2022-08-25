package com.stew.kb_common.util

import android.content.res.Resources

/**
 * Created by stew on 8/24/22.
 * mail: stewforani@gmail.com
 */
object Extension {
    fun Number.px2dp(): Float {
        val f = toFloat()
        val scale: Float = Resources.getSystem().displayMetrics.density
        return (f / scale + 0.5f)
    }

    fun Number.dp2px(): Int {
        val f = toFloat()
        val scale: Float = Resources.getSystem().displayMetrics.density
        return (f * scale + 0.5f).toInt()
    }
}