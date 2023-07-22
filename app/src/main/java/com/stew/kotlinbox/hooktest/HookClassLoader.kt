package com.stew.kotlinbox.hooktest

import android.app.Application
import dalvik.system.BaseDexClassLoader

/**
 * Created by stew on 2023/7/20.
 * mail: stewforani@gmail.com
 */
object HookClassLoader {

    fun hook(app: Application) {

        val cl = app.classLoader
        val pcl = ProxyClassLoader("", cl.parent)

        val plf = BaseDexClassLoader::class.java.getDeclaredField("pathList")
        plf.isAccessible = true
        val pl = plf.get(cl)
        plf.set(pcl, pl)

        val parentF = ClassLoader::class.java.getDeclaredField("parent")
        parentF.isAccessible = true
        parentF.set(cl, pcl)
        
    }

}