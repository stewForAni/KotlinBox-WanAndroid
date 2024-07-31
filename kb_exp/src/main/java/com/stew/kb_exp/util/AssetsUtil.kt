package com.stew.kb_exp.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream

/**
 * Created by stew on 2024/7/29.
 * mail: stewforani@gmail.com
 */
object AssetsUtil {
    fun copyAssetsToDes(context: Context,name:String,desPath:String){
        try {
            val input = context.assets.open(name)
            val output = FileOutputStream(File(desPath))
            val buffer = ByteArray(4096)
            var byteCount = 0
            while (input.read(buffer).also { byteCount = it }!= -1) {
                output.write(buffer, 0, byteCount);
            }
            output.flush()
            input.close()
            output.close()
        }catch (e:Exception){ }
    }
}