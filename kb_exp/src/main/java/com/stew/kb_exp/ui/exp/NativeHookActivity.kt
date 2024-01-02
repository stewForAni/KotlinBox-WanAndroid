package com.stew.kb_exp.ui.exp

import com.alibaba.android.arouter.facade.annotation.Route
import com.bytedance.android.bytehook.ByteHook
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.Constants
import com.stew.kb_exp.R
import com.stew.kb_exp.databinding.ActivityNhBinding
import com.stew.kb_exp.util.TestMallocUtil

/**
 * Created by stew on 2023/10/19.
 * mail: stewforani@gmail.com
 */
@Route(path = Constants.PATH_NH)
class NativeHookActivity : BaseActivity<ActivityNhBinding>() {

    override fun getLayoutID(): Int {
        return R.layout.activity_nh
    }

    override fun init() {


        //先init，再加载so，因为so文件中的方法需要  btyehook先行初始化
//        ByteHook.init()
//        System.loadLibrary("hookmalloc")
//        System.loadLibrary("testmalloc")

        mBind.imgBack.setOnClickListener { finish() }

        mBind.txStart.setOnClickListener {
            TestMallocUtil.startHook()
        }

        mBind.txTest.setOnClickListener {
            TestMallocUtil.testMalloc()
        }

    }
}