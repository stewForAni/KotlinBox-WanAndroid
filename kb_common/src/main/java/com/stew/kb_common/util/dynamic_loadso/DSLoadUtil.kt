package com.stew.kb_common.util.dynamic_loadso

import android.util.Log
import com.stew.kb_common.util.dynamic_loadso.elf.ElfParser
import java.io.File

/**
 * Created by stew on 2024/1/5.
 * mail: stewforani@gmail.com
 */
object DSLoadUtil {
    fun init(cl: ClassLoader, f: File) {

        try {
            LoadLibraryUtils.installNativeLibraryPath(cl, f)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun dsLoad(soPath: String, soFile: File) {
        var parse: ElfParser? = null
        var dp: MutableList<String>? = null
        try {
            parse = ElfParser(soFile)
            dp = parse.parseNeededDependencies()
            Log.d("DSLoadUtil", "dsLoad: $dp")
        } finally {
            parse?.close()
        }

        if (dp != null) {
            for (d in dp) {
                val name = soPath + d
                val file = File(name)
                if (file.exists()) {
                    //递归寻找依赖so
                    dsLoad(soPath, file)
                } else {
                    //非目录内，加载内置原生so
                    System.loadLibrary(d.substring(3, d.length - 3))
                }
            }
        }

        //加载当前so
        System.loadLibrary(soFile.name.substring(3, soFile.name.length - 3))
    }
}