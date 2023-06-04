package com.kb.plugin


import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by stew on 2023/6/1.
 * mail: stewforani@gmail.com
 */
class MethodTimeClassVisitor(cv: ClassVisitor) : ClassVisitor(Opcodes.ASM5, cv) {

    var className:String? = ""

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val mv =super.visitMethod(access, name, descriptor, signature, exceptions)


        return MethodTimeMethodVisitor(mv,className,name)

//        return object : AdviceAdapter(Opcodes.ASM5,mv,access,name,descriptor){
//            override fun onMethodEnter() {
//                visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
//                visitVarInsn(LSTORE, 1);
//            }
//
//            override fun onMethodExit(opcode: Int) {
//                visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
//                visitVarInsn(LSTORE, 99);
//                visitLdcInsn("ASM-MT");
//                visitTypeInsn(NEW, "java/lang/StringBuilder");
//                visitInsn(DUP);
//                visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
//                visitLdcInsn(className+"/"+name+":");
//                visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//                visitVarInsn(LLOAD, 99);
//                visitVarInsn(LLOAD, 1);
//                visitInsn(LSUB);
//                visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
//                visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
//                visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
//                visitInsn(POP);
//            }
//        }

    }
}