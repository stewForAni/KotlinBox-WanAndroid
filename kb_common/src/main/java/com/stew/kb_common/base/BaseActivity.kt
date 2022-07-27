package com.stew.kb_common.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.stew.kb_common.util.LoadingViewUtil
import com.stew.kb_common.util.StatusBarUtil

/**
 * Created by stew on 4/20/22.
 * mail: stewforani@gmail.com
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    val TAG: String = this.javaClass.simpleName
    lateinit var mBind: T

    abstract fun getLayoutID(): Int

    abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //StatusBarUtil.fitSystemBar(this)
        mBind = DataBindingUtil.setContentView(this, getLayoutID())
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBind.unbind()
    }

    fun showLoadingDialog() {
        Log.d(TAG, TAG + "showLoadingDialog: ")
        LoadingViewUtil.showLoadingDialog(this, true)
    }

    fun dismissLoadingDialog() {
        Log.d(TAG, TAG + "dismissLoadingDialog: ")
        LoadingViewUtil.dismissLoadingDialog()
    }
}