package com.stew.kotlinbox.asyncthird

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.stew.kotlinbox.KBApplication
import com.xj.anchortask.library.AnchorTask

/**
 * Created by stew on 2024/6/26.
 * mail: stewforani@gmail.com
 */
class Task7  : AnchorTask(ATConstants.TASK7) {
    override fun run() {
        KBApplication.instance!!.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Log.d(TAG, "onActivityCreated: " + activity.localClassName)
            }

            override fun onActivityStarted(activity: Activity) {
                Log.d(TAG, "onActivityStarted: " + activity.localClassName)
            }

            override fun onActivityResumed(activity: Activity) {
                Log.d(TAG, "onActivityResumed: " + activity.localClassName)
            }

            override fun onActivityPaused(activity: Activity) {
                Log.d(TAG, "onActivityPaused: " + activity.localClassName)
            }

            override fun onActivityStopped(activity: Activity) {
                Log.d(TAG, "onActivityStopped: " + activity.localClassName)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Log.d(TAG, "onActivitySaveInstanceState: " + activity.localClassName)
            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.d(TAG, "onActivityDestroyed: " + activity.localClassName)
            }

        })
    }
}