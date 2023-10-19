//
// Created by Stew on 2023/10/17.
//

#include <jni.h>
#include <android/log.h>

#define TAG "STEW_Jni_TAG" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__)

#include "bytehook.h"


bool ifHook = false;


void *malloc_proxy(size_t s) {
    //在 CPP 源文件中，也可以改为在代理函数的开头调用 BYTEHOOK_STACK_SCOPE() 宏。
    //必须在开头！！！
    BYTEHOOK_STACK_SCOPE();
    LOGD("------ size = %d ------",s);
    return BYTEHOOK_CALL_PREV(malloc_proxy, s);
}


extern "C"
JNIEXPORT void JNICALL
Java_com_stew_kb_1common_exp_TestMallocUtil_StartMalloc(JNIEnv *env, jobject thiz) {
    LOGD("------ StartMalloc ------");
    if (ifHook) {
        return;
    }
    ifHook = true;

    bytehook_stub_t s = bytehook_hook_all(
            nullptr,
            "malloc",
            reinterpret_cast<void *>(malloc_proxy),
            nullptr, nullptr);
}


