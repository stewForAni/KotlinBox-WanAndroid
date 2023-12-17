package com.stew.kotlinbox

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bytedance.android.bytehook.ByteHook
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.Constants
import com.stew.kotlinbox.databinding.ActivityExpBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by stew on 2023/10/18.
 * mail: stewforani@gmail.com
 */
@Route(path = Constants.PATH_EXP)
class ExpActivity : BaseActivity<ActivityExpBinding>() {

    override fun getLayoutID(): Int {
        return R.layout.activity_exp
    }

    override fun init() {
        mBind.imgBack.setOnClickListener { finish() }

        mBind.txNh.setOnClickListener {
            ARouter.getInstance()
                .build(Constants.PATH_NH)
                .navigation()
        }

        mBind.txPi.setOnClickListener {
            ARouter.getInstance()
                .build(Constants.PATH_PI)
                .navigation()
        }

        mBind.txDp.setOnClickListener {
            ARouter.getInstance()
                .build(Constants.PATH_DP)
                .navigation()
        }

        testSth()

    }

    private fun testSth() {

//        Log.d("mem_info_test", Runtime.getRuntime().totalMemory().toString())
//        Log.d("mem_info_test", Runtime.getRuntime().freeMemory().toString())
//        Log.d("mem_info_test", Debug.getNativeHeapSize().toString())
//        Log.d("mem_info_test", Debug.getNativeHeapFreeSize().toString())

//        runBlocking {
//            flow {
//                (1..5).forEach {
//                    //delay(200)
//                    println("emit$it,${System.currentTimeMillis()},${Thread.currentThread().name}")
//                    emit(it)
//                }
//            }.flowOn(Dispatchers.IO).collect {
//                delay(500)
//                println("collect$it,${System.currentTimeMillis()},${Thread.currentThread().name}")
//            }
//        }

        val flow = flow<Int> {
            (1..5).forEach {
                emit(it)
            }
        }
        lifecycleScope.launch {
            flow.collect {
                Log.d(TAG, "testFlow 第一个收集器: 我是冷流：$it")
            }
        }
        lifecycleScope.launch {
            flow.collect {
                Log.d(TAG, "testFlow:第二个收集器 我是冷流：$it")
            }
        }


    }

}