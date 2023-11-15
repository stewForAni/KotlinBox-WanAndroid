package com.stew.app_plugin

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter

/**
 * Created by stew on 2023/6/9.
 * mail: stewforani@gmail.com
 */
class MTMethodVisitor(
    private val cName: String,
    mv: MethodVisitor,
    access: Int,
    private val mName: String?,
    descriptor: String?
) : AdviceAdapter(Opcodes.ASM5, mv, access, mName, descriptor) {

    private var startTime = 0
    private var endTime = 0
    private var costTime = 0


    override fun visitMethodInsn(
        opcodeAndSource: Int,
        owner: String?,
        name: String?,
        descriptor: String?,
        isInterface: Boolean
    ) {
        super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
        println("owner:$owner / name:$name")
    }

    override fun onMethodEnter() {
        super.onMethodEnter()
        //long startTime = System.currentTimeMillis();
        startTime = newLocal(Type.LONG_TYPE)
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        mv.visitVarInsn(LSTORE, startTime)
    }

    override fun onMethodExit(opcode: Int) {

        //方法执行后插入
        if (opcode == RETURN) {

            //long endTime = System.currentTimeMillis();
            endTime = newLocal(Type.LONG_TYPE)
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
            mv.visitVarInsn(LSTORE, endTime)

            //long costTime = endTime - startTime;
            mv.visitVarInsn(LLOAD, endTime)
            mv.visitVarInsn(LLOAD, startTime)
            mv.visitInsn(LSUB)
            costTime = newLocal(Type.LONG_TYPE)
            mv.visitVarInsn(LSTORE, costTime)
            mv.visitLdcInsn("MT")
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder")
            mv.visitInsn(DUP)
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)
            mv.visitLdcInsn(cName + "/" + mName + "/")
            mv.visitMethodInsn(
                INVOKEVIRTUAL,
                "java/lang/StringBuilder",
                "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
                false
            )
            mv.visitVarInsn(LLOAD, costTime)
            mv.visitMethodInsn(
                INVOKEVIRTUAL,
                "java/lang/StringBuilder",
                "append",
                "(J)Ljava/lang/StringBuilder;",
                false
            )
            mv.visitLdcInsn("ms")
            mv.visitMethodInsn(
                INVOKEVIRTUAL,
                "java/lang/StringBuilder",
                "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
                false
            )
            mv.visitMethodInsn(
                INVOKEVIRTUAL,
                "java/lang/StringBuilder",
                "toString",
                "()Ljava/lang/String;",
                false
            )
            mv.visitMethodInsn(
                INVOKESTATIC,
                "android/util/Log",
                "d",
                "(Ljava/lang/String;Ljava/lang/String;)I",
                false
            )
            mv.visitInsn(POP)
            mv.visitEnd()
        }
        super.onMethodExit(opcode)
    }
}