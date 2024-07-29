package com.stew.kb_exp.ui.exp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.stew.kb_exp.R
import com.stew.kb_exp.util.PluginLoadUtil

/**
 * Created by stew on 2023/11/20.
 * mail: stewforani@gmail.com
 */
public class PluginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(PluginLoadUtil.TAG, "onCreate: ")
        setContentView(R.layout.activity_plugin)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(PluginLoadUtil.TAG, "onDestroy: ")
    }
}