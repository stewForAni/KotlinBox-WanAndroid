package com.stew.kb_common.base

import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.stew.kb_common.network.BaseResp
import com.stew.kb_common.network.RespStateData
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

    suspend fun <T> dealResp(
        block: suspend () -> BaseResp<T>,
        liveData: RespStateData<T>,
    ) {

        var result = BaseResp<T>()
        result.responseState = BaseResp.ResponseState.REQUEST_START
        liveData.value = result

        try {

            //---------------------//
            result = block.invoke()
            //---------------------//

            Log.d("BaseRepository", result.errorCode.toString() + "/" + result.errorMsg)
            when (result.errorCode) {
                Constants.HTTP_SUCCESS -> {
                    result.responseState = BaseResp.ResponseState.REQUEST_SUCCESS
                }
                Constants.HTTP_AUTH_INVALID -> {
                    result.responseState = BaseResp.ResponseState.REQUEST_FAILED
                    ToastUtil.showMsg("认证过期，请重新登录！")
                    ARouter.getInstance().build(Constants.PATH_LOGIN).navigation()
                }
                else -> {
                    result.responseState = BaseResp.ResponseState.REQUEST_FAILED
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
            result.responseState = BaseResp.ResponseState.REQUEST_ERROR
        } finally {
            liveData.value = result
        }
    }

}