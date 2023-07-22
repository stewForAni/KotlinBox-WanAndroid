package com.stew.app_plugin

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class AppPlugin :Plugin<Project>{
    override fun apply(p0: Project) {
        println(" **** AppPlugin start : "+p0.name+" **** ")
        val ex = p0.extensions.getByType(AndroidComponentsExtension::class.java)
        ex.onVariants {
            it.instrumentation.transformClassesWith(MTTransform::class.java,InstrumentationScope.PROJECT) {}
            it.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS)
        }
    }
}