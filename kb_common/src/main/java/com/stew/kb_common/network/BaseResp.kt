package com.stew.kb_common.network

/**
 * Created by stew on 6/12/22.
 * mail: stewforani@gmail.com
 */
class BaseResp<T> {
    var errorCode: Int? = null
    var errorMsg: String? = null
    var data: T? = null
}