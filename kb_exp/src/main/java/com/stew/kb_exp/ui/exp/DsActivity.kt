package com.stew.kb_exp.ui.exp

import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.bytedance.android.bytehook.ByteHook
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.Constants
import com.stew.kb_common.util.LoadingViewUtil
import com.stew.kb_common.util.ToastUtil
import com.stew.kb_common.util.dynamic_loadso.DSLoadUtil
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

        mBind.txSo.setOnClickListener {
            if (!hasClick) {
                hasClick = true
                val sourceDir = File(getExternalFilesDir(null)?.absoluteFile.toString() + "/kb_so")
                val desPath = filesDir.absoluteFile.toString()
                val desDir = File(desPath)

                //simulate download so（move so to /data/data）
                if (!File("$desPath/kb_so").exists()) {
                    FileUtils.copyDirectoryToDirectory(sourceDir, desDir)
                    //先模拟下载，再注入路径，因为路径内必须有so文件
                    DSLoadUtil.init(
                        this.classLoader,
                        File(filesDir.absoluteFile.toString() + "/kb_so")
                    )

                    //先init，再加载so，因为so文件中的方法需要 btyehook 先执行init
                    ByteHook.init()
                    //load so
                    DSLoadUtil.dsLoad("$desPath/kb_so/", File("$desPath/kb_so/libtestmalloc.so"))
                    DSLoadUtil.dsLoad("$desPath/kb_so/", File("$desPath/kb_so/libhookmalloc.so"))
                    ToastUtil.showMsg("load so successful!")
                }

            } else {
                ToastUtil.showMsg("has loaded!")
            }
        }

        mBind.imgBack.setOnClickListener { finish() }
    }
}