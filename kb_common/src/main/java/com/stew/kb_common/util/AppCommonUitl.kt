package com.stew.kb_common.util

import android.app.Application
import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate

/**
 * Created by stew on 2024/1/11.
 * mail: stewforani@gmail.com
 */
object AppCommonUitl {

    var context: Application? = null

    fun init(app: Application) {
        context = app
    }

    //kill process (Application)
    fun reStartAppByKillProcess() {
        if (context != null) {
            val clz = Class.forName("com.stew.kotlinbox .SplashActivity")
            val intent = Intent(context, clz)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context!!.startActivity(intent)
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }

    //keep process (Application)
    fun reStartAppByClearTop() {
        if (context != null) {
            val intent = context!!.packageManager.getLaunchIntentForPackage(context!!.packageName)
            intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context!!.startActivity(intent)
        }
    }

    //check system ui mode
    fun getSystemDarkMode(): Int {
        if (context != null) {
            val um = context!!.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            return um.nightMode
        }
        return AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
    }

}