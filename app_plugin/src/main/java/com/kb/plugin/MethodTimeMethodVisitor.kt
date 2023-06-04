package com.kb.plugin

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.*

/**
 * Created by stew on 2023/6/4.
 * mail: stewforani@gmail.com
 */
class MethodTimeMethodVisitor(mv: MethodVisitor, className: String?, name: String?) :MethodVisitor(Opcodes.ASM5,mv) {

    var cName:String? = ""
    var mName:String? = ""

    init {
        this.cName = className
        this.mName = name
    }

    override fun visitCode() {
        super.visitCode()

//        visitLdcInsn("ASM_TAG");
//        visitLdcInsn("start");
//        visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
//        visitInsn(POP);

        visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        visitVarInsn(LSTORE, 1);
    }

    override fun visitInsn(opcode: Int) {
        if(opcode== RETURN){
//            visitLdcInsn("ASM_TAG");
//            visitLdcInsn("end");
//            visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
//            visitInsn(POP);
            visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            visitVarInsn(LSTORE, 3);
            visitLdcInsn("ASM-MT");
            visitTypeInsn(NEW, "java/lang/StringBuilder");
            visitInsn(DUP);
            visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            visitLdcInsn(cName+"/"+mName+":");
            visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            visitVarInsn(LLOAD, 3);
            visitVarInsn(LLOAD, 1);
            visitInsn(LSUB);
            visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
            visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            visitInsn(POP);
        }
        super.visitInsn(opcode)
    }
}