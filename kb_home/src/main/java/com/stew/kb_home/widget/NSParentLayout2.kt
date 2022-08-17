package com.stew.kb_home.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParentHelper
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.stew.kb_home.R

/**
 * Created by stew on 8/17/22.
 * mail: stewforani@gmail.com
 */
class NSParentLayout2(context: Context, attr: AttributeSet) : LinearLayout(context, attr),
    NestedScrollingParent2 {

    private var npHelper: NestedScrollingParentHelper
    var p: LayoutParams
    lateinit var topView: View
    lateinit var bottomView: View
    var topViewHeight: Int = 0

    init {
        orientation = VERTICAL
        npHelper = NestedScrollingParentHelper(this)
        p = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        npHelper.onNestedScrollAccepted(child, target, axes, type)
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        npHelper.onStopNestedScroll(target, type)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        val FLAG_TOP_ON = dy > 0 && scrollY < topViewHeight
        val FLAG_TOP_OFF = dy < 0 && scrollY > 0 && !target.canScrollVertically(-1)
        if (FLAG_TOP_ON || FLAG_TOP_OFF) {
            scrollBy(0, dy)
            consumed[1] = dy
        }

        (parent as SwipeRefreshLayout).isEnabled = !(parent is SwipeRefreshLayout && scrollY > 0)
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {

    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return false
    }


    ///////////////////////////////////////////////////////////////////////////////////////////

    override fun onFinishInflate() {
        super.onFinishInflate()
        topView = findViewById(R.id.top_view)
        bottomView = findViewById(R.id.bottom_view)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        p.height = measuredHeight
        bottomView.layoutParams = p
        topViewHeight = topView.measuredHeight
    }

    override fun scrollTo(x: Int, y: Int) {
        var newY = y
        if (newY < 0) {
            newY = 0
        }
        if (newY > topViewHeight) {
            newY = topViewHeight
        }
        super.scrollTo(x, newY)
    }
    ///////////////////////////////////////////////////////////////////////////////////////////

    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        TODO("Not yet implemented")
    }

    override fun onStopNestedScroll(target: View) {
        TODO("Not yet implemented")
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        TODO("Not yet implemented")
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getNestedScrollAxes(): Int {
        TODO("Not yet implemented")
    }
}