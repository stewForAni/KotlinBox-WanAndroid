package com.stew.app_plugin

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by stew on 2023/6/8.
 * mail: stewforani@gmail.com
 */
abstract class MTTransform : AsmClassVisitorFactory<InstrumentationParameters.None> {
    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        return object : ClassVisitor(Opcodes.ASM5, nextClassVisitor) {
            val cName = classContext.currentClassData.className

            override fun visit(
                version: Int,
                access: Int,
                name: String?,
                signature: String?,
                superName: String?,
                interfaces: Array<out String>?
            ) {
                super.visit(version, access, name, signature, superName, interfaces)
                if (classFilter(cName)) {
                    if (classFilter(cName)) {
                        println("---classname = $cName")
                    }
                }
            }

            override fun visitMethod(
                access: Int,
                name: String?,
                descriptor: String?,
                signature: String?,
                exceptions: Array<out String>?
            ): MethodVisitor {
                val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
                if (classFilter(cName)&&methodFilter(name!!)) {
                    println("***method = $name")
                    return MTMethodVisitor(cName, mv, access, name, descriptor)
                }
                return mv
            }
        }
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        return true
    }

    fun classFilter(cName: String): Boolean {
        return !cName.contains("Base") && (cName.endsWith("Activity") || cName.endsWith("Fragment"))
    }

    fun methodFilter(mName: String): Boolean {
        return !mName.contains("$")&&!mName.contains("<init>")
    }

}