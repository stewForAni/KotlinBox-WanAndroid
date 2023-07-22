package com.stew.kotlinbox.hooktest

import android.util.Log
import dalvik.system.PathClassLoader

/**
 * Created by stew on 2023/7/20.
 * mail: stewforani@gmail.com
 */
class ProxyClassLoader(dexPath: String, classLoader: ClassLoader) :
    PathClassLoader(dexPath, classLoader) {
    override fun loadClass(name: String?, resolve: Boolean): Class<*> {
        val begin = System.nanoTime()
        val clz = super.loadClass(name, resolve)
        val end = System.currentTimeMillis()
        Log.d(
            "ProxyClassLoader",
            "loadClass: " + clz.name + " / " + (end - begin) + "ms / " + Thread.currentThread().id
        )
        return clz
    }
}