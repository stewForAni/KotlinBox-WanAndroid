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
        Log.d(TAG, "onCreate: ")
        //StatusBarUtil.fitSystemBar(this)
        mBind = DataBindingUtil.setContentView(this, getLayoutID())
        init()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
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