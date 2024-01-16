package com.stew.kb_common.util

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
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
    //recreate app activity
    //如果在singleTask模式下的MainActivity里直接调用，app直接会关闭，但没crash
    //二级页面表现正常
    fun reStartAppByKillProcess() {
        if (context != null) {
            val clz = Class.forName("com.stew.kotlinbox.MainActivity")
            val intent = Intent(context, clz)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context!!.startActivity(intent)
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }

    //keep process (Application)
    //no create app activity,execute activity onStart newIntent onResume
    //如果在singleTask模式下的MainActivity里直接调用，和二级页面表现一致，activity 走 onStart newIntent onResume
    fun reStartAppByClearTop() {
        if (context != null) {
            val intent = context!!.packageManager.getLaunchIntentForPackage(context!!.packageName)
            intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context!!.startActivity(intent)
        }
    }

    //bad bug
    fun reStartAppByAlarm() {
//        if (context != null) {
//            val intent = context!!.packageManager.getLaunchIntentForPackage(context!!.packageName)
//            intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            val pIntent = PendingIntent.getActivity(
//                context,
//                0,
//                intent,
//                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
//            )
//            val alm = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alm.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, pIntent)
//            android.os.Process.killProcess(android.os.Process.myPid())
//        }
    }

    //check system ui mode
    fun getSystemDarkMode(): Int {
        if (context != null) {
            val um = context!!.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            return um.nightMode
        }
        return AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
    }


    fun setAppDarkMode(modeNightNo: Int) {
        KVUtil.put(Constants.USER_DARK_MODE, modeNightNo)
        AppCompatDelegate.setDefaultNightMode(modeNightNo)
    }

    fun setFollowSystemTheme() {
        KVUtil.put(Constants.USER_DARK_MODE, 1000)
        when (getSystemDarkMode()) {
            AppCompatDelegate.MODE_NIGHT_NO -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )

            AppCompatDelegate.MODE_NIGHT_YES -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )

            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}