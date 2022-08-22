package com.stew.kb_common.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.stew.kb_common.network.BaseResp
import com.stew.kb_common.util.Constants
import com.stew.kb_common.util.ToastUtil


/**
 * Created by stew on 7/30/22.
 * mail: stewforani@gmail.com
 */
open class BaseRepository {

    private val SUCCESS = 0
    private val AUTH_INVALID = -1001

    suspend fun <T> dealResp(
        block: suspend () -> BaseResp<T>,
        liveData: MutableLiveData<T>
    ) {
        val result = block.invoke()
        when (result.errorCode) {
            SUCCESS -> {
                Log.d("BaseRepository", "dealResp: success" + result.errorCode)
                Log.d("BaseRepository", "dealResp: success" + result.errorMsg)
                Log.d("BaseRepository", "dealResp: success" + result.data)
                liveData.value = result.data
            }
            AUTH_INVALID -> {
                Log.d("BaseRepository", "dealResp: auth" + result.errorCode)
                Log.d("BaseRepository", "dealResp: auth" + result.errorMsg)
                Log.d("BaseRepository", "dealResp: auth" + result.data)
                ToastUtil.showMsg("认证过期，请重新登录！")
                ARouter.getInstance()
                    .build(Constants.PATH_LOGIN)
                    .navigation()
            }
            else -> {
                Log.d("BaseRepository", "dealResp: error")
                ToastUtil.showMsg("code:" + result.errorCode.toString() + " / msg:" + result.errorMsg)
            }
        }
    }

}