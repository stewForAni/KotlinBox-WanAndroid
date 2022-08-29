package com.stew.kb_common.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by stew on 7/29/22.
 * mail: stewforani@gmail.com
 */

typealias vmBLOCK = suspend () -> Unit

open class BaseViewModel : ViewModel() {

    //fun 默认public，这里使用protected
    protected fun launch(
        block: vmBLOCK
    ) {
        //使用viewModelScope需要添加依赖androidx.navigation:navigation-fragment-ktx
        //这里不需要指定viewModelScope.launch(Dispatchers.IO)，因为retrofit自身会在子线程进行网络请求
        viewModelScope.launch {
            try {
                block.invoke()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    private fun onError(e: Exception) {
        Log.d("onError", "onError: $e")
    }
}

