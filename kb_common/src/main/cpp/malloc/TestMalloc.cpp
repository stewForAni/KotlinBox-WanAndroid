//
// Created by Stew on 2023/10/17.
//

#include <jni.h>
#include <malloc.h>
#include <android/log.h>
#define TAG "STEW_Jni_TAG" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__)

extern "C"
JNIEXPORT void JNICALL
Java_com_stew_kb_1common_exp_TestMallocUtil_TestMalloc(JNIEnv *env, jobject thiz) {
LOGD("------ TestMalloc ------");
malloc(88*1024*1024);
}