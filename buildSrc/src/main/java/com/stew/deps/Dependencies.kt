package com.stew.deps

object Versions {
    const val KGP = "1.7.21"
    //const val KGP = "1.4.32"
}

object ProjectConfigs {

    const val KGP = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KGP}"

    const val compileSdkVersion = 33
    const val buildToolsVersion = "33.0.2"
    const val minSdkVersion = 21
    const val targetSdkVersion = 33
    const val versionCode = 2
    const val versionName = "1.1"

}

object Libs {

    //base
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KGP}"
    const val core = "androidx.core:core-ktx:1.2.0"
    const val material = "com.google.android.material:material:1.2.1"
    const val appcompat = "androidx.appcompat:appcompat:1.3.0"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"

    //navigation
    const val navigation_fg = "androidx.navigation:navigation-fragment-ktx:2.3.5"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:2.3.5"

    // 协程Android支持库
    const val kotlinx_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3"
    const val kotlinx_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3"

    //koin with mavenCentral
    const val koin_android = "io.insert-koin:koin-android:3.4.1"

    //banner
    const val bannerviewpager = "com.github.zhpanvip:bannerviewpager:3.5.1"

    //coil
    const val coil = "io.coil-kt:coil:1.2.1"

    //roundedimageview
    const val roundedimageview = "com.makeramen:roundedimageview:2.3.0"

    //flex
    const val flexbox = "com.google.android.flexbox:flexbox:3.0.0"

    //swiperefreshlayout
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    //ARouter
    const val arouter_api = "com.alibaba:arouter-api:1.5.2"
    const val arouter_compiler = "com.alibaba:arouter-compiler:1.5.2"

    //mmkv
    const val mmkv = "com.tencent:mmkv:1.2.10"

}
