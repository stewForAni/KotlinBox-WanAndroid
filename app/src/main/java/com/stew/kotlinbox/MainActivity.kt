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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //函数
        Log.d("test1", test1(1, 2).toString())
        Log.d("test2", test2(1, 2).toString())

        //字符串模版，可以包含模板表达式，即一小段代码
        Log.d("test2", "test2(1, 2) = ${test2(2, 3)}")

        //条件表达式
        Log.d("test3", maxOF1(8, 9).toString())
        Log.d("test3", maxOF2(8, 9).toString())

        //空安全
        test4()

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

    private fun test4(){

        //表示b可以为null
        var b:String = "abcd"
        var a:String? = "abcd"
        a = null

        //要么显式的去判空，要么使用安全调用操作符[?.]
        Log.d("test4", a?.length.toString())
        //b本身为非空，这里会提示无需使用？
        Log.d("test4", b?.length.toString())

        //bob?.department?.head?.name
        //这种链式调用，使用？很有用，任何一个变量为null，这个调用就会返回null


    }
}