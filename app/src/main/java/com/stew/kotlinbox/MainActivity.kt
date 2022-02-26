package com.stew.kotlinbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    //只读局部变量 val
    val a: Int = 0
    val b = 0

    //可重新赋值的变量 var
    var c = 3

    var d = "lll"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //函数
        //Log.d("test1", test1(1, 2).toString())
        //Log.d("test2", test2(1, 2).toString())

        //字符串模版，可以包含模板表达式，即一小段代码
        //Log.d("test2", "test2(1, 2) = ${test2(2, 3)}")

        //条件表达式
        //Log.d("test3", maxOF1(8, 9).toString())
        //Log.d("test3", maxOF2(8, 9).toString())

        //空安全
        //test4()

        //Elvis 操作符 [?:]
        //test6()

        //!!操作符,会返回一个NPE
        //test7()

        //【is运算符】类型检测与自动类型转换
        //Log.d("test8",test8("12345")?.length.toString())
        //Log.d("test8", test8(12345)?.length.toString())

        test9()

    }

    private fun test9() {
        val list = listOf("1", "2", "3")
        for (item in list) {
            Log.d("test9", item)
        }

        for (index in list.indices){
            Log.d("test9", list[index])
        }
    }

    private fun test1(a: Int, b: Int): Int {
        return a + b
    }

    private fun test2(a: Int, b: Int) = a + b

    private fun maxOF1(a: Int, b: Int): Int {
        if (a > b) {
            return a
        } else {
            return b
        }
    }

    private fun maxOF2(a: Int, b: Int) = if (a > b) a else b

    private fun test4() {

        //表示b可以为null
        var b: String = "abcd"
        var a: String? = "abcd"
        a = null

        //要么显式的去判空，要么使用安全调用操作符[?.]，这里不加？编译不过
        Log.d("test4", a?.length.toString())
        //b本身为非空，这里会提示无需使用？
        Log.d("test4", b.length.toString())

        //bob?.department?.head?.name
        //这种链式调用，使用？很有用，任何一个变量为null，这个调用就会返回null
        //如果要只对非空值执行某个操作，安全调用操作符可以与 let 一起使用：
        val list: List<String?> = listOf("111", null, "222")
        for (item in list) {
            item?.let { Log.d("test4", it) }
        }

        //如果调用链中的任何一个接收者为空都会跳过赋值，而右侧的表达式根本不会求值
        person()?.department()?.d = test5()

    }

    private fun test5(): String {
        Log.d("test5", "---------")
        return "000"
    }

    fun person(): MainActivity? {
        return this
    }

    fun department(): MainActivity? {
        return null
    }

    private fun test6() {
        var a: String? = "abcd"
        a = null
        Log.d("test6", (a?.length ?: 1).toString())
    }

    private fun test7() {
        var a: String? = null
        Log.d("test7", a?.length.toString())
    }

    //【is运算符】类型自动转换，在符合条件分支下，obj自动转换为String类型，无需显式转换
    private fun test8(obj: Any): String? = if (obj is String) obj else null

}