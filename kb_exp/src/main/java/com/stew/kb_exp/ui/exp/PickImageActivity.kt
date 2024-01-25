package com.stew.kb_exp.ui.exp

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.activity.result.contract.ActivityResultContracts
import com.alibaba.android.arouter.facade.annotation.Route
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.Constants
import com.stew.kb_exp.R
import com.stew.kb_exp.databinding.ActivityPickImageBinding
import java.io.InputStream

/**
 * Created by stew on 2024/1/25.
 * mail: stewforani@gmail.com
 */
@Route(path = Constants.PATH_PICK_IMAGE)
class PickImageActivity : BaseActivity<ActivityPickImageBinding>() {
    override fun getLayoutID(): Int {
        return R.layout.activity_pick_image
    }

    override fun init() {

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                if (it.data != null && it.data!!.data != null) {
                    var i: InputStream? =null
                    try {
                         i = contentResolver.openInputStream(it.data!!.data!!) as InputStream
                        mBind.image.setImageBitmap(BitmapFactory.decodeStream(i))
                    }catch (e:Exception){
                        e.printStackTrace()
                    }finally {
                        i?.close()
                    }

                }

            }
        }

        mBind.imgBack.setOnClickListener { finish() }
        mBind.txOpen.setOnClickListener {
            val i = Intent(Intent.ACTION_OPEN_DOCUMENT)
            i.addCategory(Intent.CATEGORY_OPENABLE)
            i.type = "image/*"
            launcher.launch(i)
        }
    }
}