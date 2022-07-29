package com.stew.kb_common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by stew on 7/29/22.
 * mail: stewforani@gmail.com
 */

typealias BLOCK<T> = suspend () -> T
typealias ERROR = suspend () -> Unit

class BaseViewModel : ViewModel() {
    fun launch(
        block: BLOCK<Unit>,
        error: ERROR
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                error(e)
            }
        }
    }
}

