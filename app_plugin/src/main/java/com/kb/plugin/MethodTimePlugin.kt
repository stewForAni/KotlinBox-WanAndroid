package com.kb.plugin

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import java.io.FileOutputStream

/**
 * Created by stew on 2023/5/31.
 * mail: stewforani@gmail.com
 */
class MethodTimePlugin : Plugin<Project>, Transform() {

    override fun apply(target: Project) {
        val ext = if (target.plugins.hasPlugin("com.android.application")) {
            println("****** App MethodTimePlugin Start ******")
            target.extensions.getByType(AppExtension::class.java)
        } else {
            println("****** Library MethodTimePlugin Start ******")
            target.extensions.getByType(LibraryExtension::class.java)
        }
        ext.registerTransform(this)
    }

    override fun getName(): String {
        return "MethodTimePlugin"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.PROJECT_ONLY
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun transform(transformInvocation: TransformInvocation?) {
        val input = transformInvocation?.inputs
        val output = transformInvocation?.outputProvider
        input?.forEach { transformInput ->
            transformInput.jarInputs.forEach {
                val dest = output?.getContentLocation(
                    it.name,
                    it.contentTypes,
                    it.scopes,
                    Format.JAR
                )
                FileUtils.copyFile(it.file, dest)
            }
            transformInput.directoryInputs.forEach {
                if (it.file.isDirectory) {
                    FileUtils.getAllFiles(it.file).forEach { file ->
                        if (!file.name.contains("Base")&&(file.name.endsWith("Activity.class") ||
                                    file.name.endsWith("Fragment.class"))
                        ) {
                            println("file = " + file.name)
                            val cr = ClassReader(file.readBytes())
                            val cw = ClassWriter(cr,ClassWriter.COMPUTE_MAXS)
                            val cs = MethodTimeClassVisitor(cw)
                            cr.accept(cs,ClassReader.EXPAND_FRAMES)
                            val byte = cw.toByteArray()
                            val fos = FileOutputStream(file.absolutePath)
                            fos.write(byte)
                            fos.flush()
                            fos.close()
                        }
                    }
                }
                val dest = output?.getContentLocation(
                    it.name,
                    it.contentTypes,
                    it.scopes,
                    Format.DIRECTORY
                )
                FileUtils.copyDirectory(it.file, dest)
            }
        }

    }
}