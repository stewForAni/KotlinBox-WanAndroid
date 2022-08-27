package com.stew.kb_common.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.stew.kb_common.network.BaseResp
import com.stew.kb_common.util.Constants
import com.stew.kb_common.util.ToastUtil
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException


/**
 * Created by stew on 7/30/22.
 * mail: stewforani@gmail.com
 */
open class BaseRepository {

    private val SUCCESS = 0
    private val AUTH_INVALID = -1001


    suspend fun <T> dealResp(
        block: suspend () -> BaseResp<T>,
        liveData: MutableLiveData<T>,
    ) {
        this.dealResp(block, liveData, null)
    }

    suspend fun <T> dealResp(
        block: suspend () -> BaseResp<T>,
        liveData: MutableLiveData<T>,
        load: MutableLiveData<Boolean>?
    ) {

        try {

            if (load != null) { load.value = true }
            val result = block.invoke()
            if (load != null) { load.value = false }

            when (result.errorCode) {
                SUCCESS -> {
                    Log.d("BaseRepository", "success" + result.errorCode + "/" + result.errorMsg)
                    liveData.value = result.data
                }
                AUTH_INVALID -> {
                    Log.d("BaseRepository", "auth" + result.errorCode + "/" + result.errorMsg)
                    ToastUtil.showMsg("认证过期，请重新登录！")
                    ARouter.getInstance().build(Constants.PATH_LOGIN).navigation()
                }
                else -> {
                    Log.d("BaseRepository", "dealResp: error")
                    ToastUtil.showMsg("code:" + result.errorCode.toString() + " / msg:" + result.errorMsg)
                }
            }
        } catch (e: Exception) {
            Log.d("BaseRepository", "dealResp: Exception$e")
            when (e) {
                is UnknownHostException,
                is HttpException,
                is ConnectException,
                -> {
                    //网络error
                    ToastUtil.showMsg("网络错误！")
                }
                else -> {
                    ToastUtil.showMsg("未知错误！")
                }

            }
        }
    }

}