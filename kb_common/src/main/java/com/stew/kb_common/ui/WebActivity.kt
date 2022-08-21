package com.stew.kb_common.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Route
import com.stew.kb_common.R
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.databinding.ActivityWebBinding
import com.stew.kb_common.util.Constants

/**
 * Created by stew on 8/18/22.
 * mail: stewforani@gmail.com
 */
@Route(path = Constants.PATH_WEB)
@SuppressLint("SetJavaScriptEnabled")
class WebActivity : BaseActivity<ActivityWebBinding>() {

    lateinit var web: WebView

    override fun getLayoutID(): Int {
        return R.layout.activity_web
    }


    override fun init() {
        if (intent == null) {
            return
        }
        val t = intent.getStringExtra(Constants.WEB_TITLE)
        val l = intent.getStringExtra(Constants.WEB_LINK).toString()

        Log.d(TAG, "t: $t")
        Log.d(TAG, "l: $l")

        mBind.imgBack.setOnClickListener { finish() }
        mBind.txWebTitle.text = t



        web = WebView(applicationContext)
        mBind.webContainer.addView(web)

        web.settings.javaScriptEnabled = true
        web.settings.loadWithOverviewMode = true
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true

        web.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.d(TAG, "onPageStarted: ")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d(TAG, "onPageFinished: ")
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.d(TAG, "shouldOverrideUrlLoading: =====1")
                return true
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                Log.d(TAG, "shouldOverrideUrlLoading: =====2")
                if (!l.equals(request?.url)) {
                    Log.d(TAG, "shouldOverrideUrlLoading: =====3")
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(request?.url.toString()))
                    )
                    return true
                }
                return false
            }
        }

        web.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                Log.d(TAG, "onProgressChanged: $newProgress")
                mBind.pb.progress = newProgress
                if (newProgress == 100) {
                    mBind.pb.visibility = View.GONE
                }
            }
        }

        web.loadUrl(l)

    }

    override fun onDestroy() {
        mBind.webContainer.removeAllViews()
        web.clearHistory()
        web.destroy()
        super.onDestroy()
    }
}