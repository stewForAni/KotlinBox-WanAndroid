package com.stew.kb_common.util

import android.app.Application
import android.widget.Toast

/**
 * Created by stew on 7/27/22.
 * mail: stewforani@gmail.com
 */
object ToastUtil {
    lateinit var mContext:Application
    fun init(context: Application){
        mContext= context
    }

    fun showMsg(str:String){
        Toast.makeText(mContext,str,Toast.LENGTH_SHORT).show()
    }
}