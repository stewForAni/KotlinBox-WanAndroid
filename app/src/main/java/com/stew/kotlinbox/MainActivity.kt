package com.stew.kotlinbox

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

        //for循环
        //test9()

        //while循环
        //test10()

        //when表达式
        //Log.d("test11",test11("1"))
        //Log.d("test11",test11(1))
        //Log.d("test11",test11(2))

        //使用区间（range）
        //test12()

        //集合
        //test13()

        //map
        //test14()

        //test15()

        //Break 与 Continue 标签
        //test16()


        //return
        //test17()

//let函数测试------------------------------
//        val s = stew("ww", "cc")
//        s.let {
//            Log.d("stew--",s.toString())
//            Log.d("stew--",it.toString())
//            s.a()
//            it.a()
//            Log.d("stew--",it.toString())
//        }
//        s.run {
//            Log.d("stew--",s.toString())
//            Log.d("stew--",this.toString())
//            s.a()
//            this.a()
//            Log.d("stew--",this.toString())
//        }
//        s.let {
//            it.a()
//            it.b()
//        }
//        deals(s)
//        deals(null)

//also函数测试------------------------------

//        Log.d("stew--", s.toString())
//        val str = s.also {
//            it.a()
//            it.b()
//            "9999"
//        }.a()
//        Log.d("stew--", str.toString())
        //also函数返回对象本身


//with函数测试------------------------------
//调用同一个对象的多个方法 / 属性时，可以省去对象名重复，直接调用方法名 / 属性即可
//        with(s){
//            Log.d("stew--", s1)
//            Log.d("stew--", s2)
//        }


//run函数测试------------------------------
//        结合了let、with两个函数的作用，即：
//        调用同一个对象的多个方法 / 属性时，可以省去对象名重复，直接调用方法名 / 属性即可
//        定义一个变量在特定作用域内
//        统一做判空处理
//        返回值 = 函数块的最后一行

//        val y = s.run {
//            Log.d("stew--", s1)
//            a()
//            "sssss"
//        }
//        Log.d("stew--", y.toString())

//apply函数测试------------------------------
//与run类似但区别在于返回值：
//run函数返回最后一行的值 / 表达式
//apply函数返回传入的对象的本身

//派生类初始化顺序------------------------------
//        val t = TestClass("stew", "12")
//        t.fun2()
//        t.draw()

//测试 SAM  函数式接口------------------------------
//        val tc = TestasmClass()
//        Log.d("stew---", tc.test1().toString())
//
//
//        val tc1 = object : testasm {
//            override fun test1(): Int {
//                Log.d("stew---", "TestasmClass fun test2")
//                return 200
//            }
//        }
//
//        Log.d("stew---", tc1.test1().toString())
//
//
//        val tc2 = testasm {
//            Log.d("stew---", "TestasmClass fun test3")
//            300
//        }
//
//        Log.d("stew---", tc2.test1().toString())


//测试扩展函数------------------------------
//        val list = mutableListOf(1, 2, 3)
//        Log.d("stew---", list.toString())
//        list.swap(0, 1)
//        Log.d("stew---", list.toString())
//
//
//        val listS = mutableListOf("a", "b", "c")
//        Log.d("stew---", listS.toString())
//        listS.swap(0, 1)
//        Log.d("stew---", listS.toString())

//        val BaseClass("ww").se: Int
//            get() = size - 1

//        val bb = BaseClass("jj")
//        Log.d("stew---", bb.ss.toString())
//测试枚举类------------------------------
//        Log.d("stew---", Direction.NORTH.ordinal.toString())
//        Log.d("stew---", Direction.SOUTH.ordinal.toString())
//        Log.d("stew---", Direction.WEST.toString())
//        Log.d("stew---", Direction.EAST.toString())

//测试类------------------------------
//        test20()
//        val x1 = foo().x        // 没问题
//        val x2 = publicFoo().x  // 错误：未能解析的引用“x”
//单例模式------------------------------
//        Test21.fun1()
//        Log.d("stew---", Test21.toString())
//        Test21.fun2()
//        Log.d("stew---", Test21.toString())
//伴生对象------------------------------
//        val instance = Test22
//        instance.fun1()
//相关函数测试------------------------------
//        yy()

        runBlocking {
            Log.d("runBlocking", "启动一个协程")
        }
        GlobalScope.launch {
            Log.d("launch", "启动一个协程")
        }
        GlobalScope.async {
            Log.d("async", "启动一个协程")
        }
    }


    fun yy(y1: Int = 9, y2: Int = 8) {
        Log.d("stew---", "y1:" + y1)
        Log.d("stew---", "y2:" + y2)

    }

    fun yy(y1: Int) {
        Log.d("stew---", "y1:" + y1)
    }

    class Test22 {
        companion object {
            fun fun1() {
                Log.d("stew---", "test companion object")
            }
        }
    }

    object Test21 {
        fun fun1() {
            Log.d("stew---", "test 1 singleton")
        }

        fun fun2() {
            Log.d("stew---", "test 2 singleton")
        }
    }

    // 私有函数，所以其返回类型是匿名对象类型
    private fun foo() = object {
        val x: String = "x"
    }

    // 公有函数，所以其返回类型是 Any
    fun publicFoo() = object {
        val x: String = "x"
    }


    fun test20() {
        val test = object {
            val a = 1
            val b = 2
        }

        Log.d("stew---", test.a.toString())
        Log.d("stew---", test.b.toString())
    }

    enum class Direction(i: Int) {
        NORTH(1),
        SOUTH(2),
        WEST(3),
        EAST(4)
    }

    val BaseClass.ss: Int
        get() = 1111111111


    //扩展函数
    fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
        val tmp = this[index1]
        this[index1] = this[index2]
        this[index2] = tmp
    }

    //函数式接口
    fun interface testasm {
        fun test1(): Int
    }

    class TestasmClass : testasm {
        override fun test1(): Int {
            Log.d("stew---", "TestasmClass fun test1")
            return 100
        }

    }

    open class BaseClass(val name: String) {
        init {
            Log.d("stew---", "BaseClass init")
        }

        open var a: String = "00000000"

        fun fun1() {
            Log.d("stew---", "fun1------${a}")
        }

        open fun draw() {
            Log.d("stew---", "base class draw")
        }
    }

    interface BaseInterface {
        fun draw() {
            Log.d("stew---", "base inter draw")
        }
    }

    class TestClass(name: String, age: String) :
        BaseClass(name.also { Log.d("stew---", "Base class construct") }), BaseInterface {
        init {
            Log.d("stew---", "TestClass init")
        }

//        override var a: String = name.also { Log.d("stew---", "TestClass a var init") }

        fun fun2() {
            super.fun1()
            fun1()

            Log.d("stew---", "fun2------$a")

            var o = a
            val t = TestInnerClas()
            t.innerFun1()
        }

        inner class TestInnerClas {
            fun innerFun1() {
                Log.d("stew---", "innerFun1------$a")
                super@TestClass.fun1()
            }
        }

        //
        override fun draw() {
            Log.d("stew---", "TestClass draw")
            super<BaseInterface>.draw()
            super<BaseClass>.draw()
        }
    }


    //类相关测试------------------------------
//如果派生类有一个主构造函数，其基类必须用派生类主构造函数的参数就地初始化。
    open class human(a: String, b: String) {}
    class kid(a: String, b: String) : human(a, b) {}
    open class human1(a: String) {
        constructor(a: String, b: String) : this(a) {}
    }

    class kid1(a: String) : human1(a) {}

    class myView : View {
        constructor(c: Context) : super(c)
        constructor(c: Context, a: AttributeSet) : super(c, a)
    }

    //覆盖属性，方法相关测试------------------------------
//override前加上final，可以禁止覆盖！
    open class human2 {
        open val w: Int = 0
        open fun a() {

        }

        fun b() {

        }
    }

    open class kid2 : human2() {
        override val w: Int = 9
        override fun a() {

        }

    }

    class kid3 : kid2() {
        override val w: Int = 8
        override fun a() {

        }

    }

//覆盖属性相关测试----------------------------

//------------------------------------------

    private fun deals(s: stew?) {
        Log.d("stew--", "deals")
        val str = s?.let {
            it.a()
            it.b()
            "mmmmmm"
        }
        Log.d("stew--", str.toString())
        //let函数，返回最后一行
    }

    class stew(var name: String, var age: String) {

        val s1 = name
        val s2 = age

        init {
            Log.d("stew--", "stew init")
        }

        fun a() {
            Log.d("stew--", "fun $name")
        }

        fun b() {
            Log.d("stew--", "fun $age")
        }

    }

    class hellen {
        init {
            Log.d("stew--", "hellen init")
        }

        fun s() {

        }
    }

    private fun test17() {

//        listOf(1, 2, 3, 4, 5).forEach {
//            if (it == 3) return@forEach
//            print(it)
//        }

        listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {
            if (value == 3) return  // 局部返回到匿名函数的调用者，即 forEach 循环
            print(value)
        })

        Log.d("test17", ".........s........")

    }

    private fun test16() {
        stew@ for (i in 1..5) {
            for (j in 1..5) {
                Log.d("test16", j.toString())
                if (j == 2) break@stew
            }
        }

//        for (i in 1..5) {
//            for (j in 1..5) {
//                Log.d("test16", j.toString())
//                if (j == 2) break
//            }
//        }
    }

    private fun test15() {

        val a = 1_000_000;
        Log.d("test15", a.toString())

        val b: Int = 1
        //不能直接赋值，需要显式转换
        val c: Long = b.toLong()

        // 5/2 = 2 ,必须显式转换为浮点类型
        val x = 5 / 2.toFloat()
        Log.d("test15", x.toString())

    }

    private fun test14() {
        val map = mapOf("a" to 111, "b" to 2, "c" to 3)
        Log.d("test14", map["a"].toString())

        val map1 = mapOf(null to "a", 2 to "b", 3 to "c")
        Log.d("test14", map1.get(null).toString())

        //kv 都可以为null
    }

    private fun test13() {
        val list = listOf("1", "2", "3", "4")

        when {
//            "1" in list -> Log.d("test13","1 YES")
//            "3" in list -> Log.d("test13","3 YES")
//            "4" in list -> Log.d("test13","4 YES")
            "5" in list -> Log.d("test13", "5 YES")
        }


        val list1 = listOf("abc", "afgg", "bgg", "kioo", "aao")
        list1.filter { it.startsWith("a") }.sortedBy { it }.map { it.toUpperCase() }.forEach {
            Log.d("test13", it)
        }

    }

    private fun test12() {
//        val a = 10
//        val b = 9
//        if(a in 0..b+1){
//            Log.d("test12","YES")
//        }else{
//            Log.d("test12","NO")
//        }
//
//        val list = listOf("1", "2", "3", "4")
//
//        if(4 !in 0..list.size){
//            Log.d("test12","4 index out of list size")
//        }
//
//        if(4 !in list.indices){
//            Log.d("test12","4 index out of list indices")
//        }
//
//        for (item in list.indices){
//            Log.d("test12",item.toString())
//        }
//        for (i in 0..10 step 2){
//            Log.d("test12",i.toString())
//        }

        for (i in 10 downTo 0 step 4) {
            Log.d("test12", i.toString())
        }
    }

    private fun test11(obj: Any): String =
        when (obj) {
            "1" -> "haha"
            1 -> "heihei"
            else -> "0"
        }


    private fun test10() {
        val list = listOf("1", "2", "3", "4")
        var index = 0

        while (index < list.size) {
            Log.d("test10", list[index])
            index++
        }

    }

    private fun test9() {
        val list = listOf("1", "2", "3")
        for (item in list) {
            Log.d("test9", item)
        }

        //list.indices 为下标集合
        for (index in list.indices) {
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
