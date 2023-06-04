# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#---------------------------------基本指令区----------------------------------

## 指定代码的压缩级别 0 - 7(指定代码进行迭代优化的次数，在Android里面默认是5，这条指令也只有在可以优化时起作用。)
#-optimizationpasses 5
## 混淆时不会产生形形色色的类名(混淆时不使用大小写混合类名)
#-dontusemixedcaseclassnames
## 指定不去忽略非公共的库类(不跳过library中的非public的类)
#-dontskipnonpubliclibraryclasses
## 指定不去忽略非公共的的库类的成员
#-dontskipnonpubliclibraryclassmembers
##不进行预校验,Android不需要,可加快混淆速度。
#-dontpreverify
## 混淆时记录日志(打印混淆的详细信息)
## 这句话能够使我们的项目混淆后产生映射文件
## 包含有类名->混淆后类名的映射关系
#-verbose
#-printmapping proguardMapping.txt
## 指定混淆是采用的算法，后面的参数是一个过滤器
## 这个过滤器是谷歌推荐的算法，一般不做更改
#-optimizations !code/simplification/cast,!field/*,!class/merging/*
#
#
##保护代码中的 Annotation 内部类不被混淆
#-keepattributes *Annotation*,InnerClasses
#-ignorewarnings
## 避免混淆泛型，这在 JSON 实体映射时非常重要，比如 fastJson
#-keepattributes Signature
## 抛出异常时保留代码行号，在异常分析中可以方便定位
#-keepattributes SourceFile,LineNumberTable
##----------------------------------------------------------------------------
#
##---------------------------------默认保留区---------------------------------
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class * extends android.view.View
#-keep public class com.android.vending.licensing.ILicensingService
#-keep class android.support.** {*;}
#
## 保留所有的本地 native 方法不被混淆
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
## 保留在 Activity 中的方法参数是 view 的方法，
## 从而我们在 layout 里面编写 onClick 就不会被影响
#-keepclassmembers class * extends android.app.Activity{
#    public void *(android.view.View);
#}
## 枚举类不能被混淆
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
## 保留自定义控件（继承自 View）不被混淆
#-keep public class * extends android.view.View{
#    *** get*();
#    void set*(***);
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
## 保留 Parcelable 序列化的类不被混淆
#-keep class * implements android.os.Parcelable {
#  *;
#}
## 保留 Serializable 序列化的类不被混淆
#-keep class * implements java.io.Serializable { *;}
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
## 对于 R（资源）下的所有类及其方法，都不能被混淆
#-keep class **.R$* {
# *;
#}
## 对于带有回调函数 onXXEvent 的，不能被混淆
#-keepclassmembers class * {
#    void *(**On*Event);
#}
#
#-keepclassmembers class * {
#   public <init>(org.json.JSONObject);
#}
#
#-keepattributes *JavascriptInterface*
