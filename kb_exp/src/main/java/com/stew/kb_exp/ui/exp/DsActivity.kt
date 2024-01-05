package com.stew.kb_exp.ui.exp

import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.Constants
import com.stew.kb_common.util.LoadingViewUtil
import com.stew.kb_common.util.ToastUtil
import com.stew.kb_exp.R
import com.stew.kb_exp.databinding.ActivityDsBinding
import org.apache.commons.io.FileUtils
import java.io.File

/**
 * Created by stew on 2024/1/1.
 * mail: stewforani@gmail.com
 */
@Route(path = Constants.PATH_DS)
class DsActivity : BaseActivity<ActivityDsBinding>() {

    var hasClick = false

    override fun getLayoutID(): Int {
        return R.layout.activity_ds
    }

    override fun init() {

        //内
        Log.d("DsActivity", "getFilesDir: $filesDir")
        //外私
        Log.d("DsActivity", "getExternalFilesDir: " + getExternalFilesDir(null))

        mBind.imgBack.setOnClickListener { finish() }

        mBind.txSo.setOnClickListener {
            if (!hasClick) {
                hasClick = true
                val sourceDir = File(getExternalFilesDir(null)?.absoluteFile.toString() + "/kb_so")
                val desPath = filesDir.absoluteFile.toString()
                val desDir = File(desPath)
                Log.d(TAG, "init: 1")
                if (!File("$desPath/kb_so").exists()) {
                    Log.d(TAG, "init: 2")
                    LoadingViewUtil.showLoadingDialog(this, false)
                    FileUtils.copyDirectoryToDirectory(sourceDir, desDir)
                    Thread.sleep(2000)
                    LoadingViewUtil.dismissLoadingDialog()
                }
            } else {
                ToastUtil.showMsg("has loaded")
            }
        }
    }
}