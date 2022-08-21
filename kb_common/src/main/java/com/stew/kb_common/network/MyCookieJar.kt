package com.stew.kb_common.network

import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * Created by stew on 8/21/22.
 * mail: stewforani@gmail.com
 */
class MyCookieJar:CookieJar {

    private val cookieStore: HashMap<String, List<Cookie>> = HashMap()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        Log.d("OkHttp", "loadForRequest:")
        val cookies = cookieStore[url.host]
        return cookies ?: ArrayList()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        Log.d("OkHttp", "saveFromResponse: $cookies")
        cookieStore[url.host] = cookies;
    }
}