package com.stew.kotlinbox.asyncthird

import androidx.appcompat.app.AppCompatDelegate
import com.stew.kb_common.util.AppCommonUitl
import com.stew.kb_common.util.Constants
import com.stew.kb_common.util.KVUtil
import com.xj.anchortask.library.AnchorTask

/**
 * Created by stew on 2024/6/26.
 * mail: stewforani@gmail.com
 */
class Task6  : AnchorTask(ATConstants.TASK6) {
    override fun run() {
        when (val userDarkMode = KVUtil.getInt(Constants.USER_DARK_MODE, 999)) {
            999 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            1000 -> AppCommonUitl.setFollowSystemTheme()
            else -> AppCompatDelegate.setDefaultNightMode(userDarkMode)
        }
    }
}