package com.stew.kb_common.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding

/**
 * Created by stew on 7/30/22.
 * mail: stewforani@gmail.com
 */
abstract class BaseVMFragment<T : ViewDataBinding> : BaseFragment<T>() {

    abstract fun init()
    abstract fun observe()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observe()
    }

    override fun onResume() {
        super.onResume()

        //fragment懒加载，待定
        lazyLoad()
    }

    open fun lazyLoad(){

    }
}