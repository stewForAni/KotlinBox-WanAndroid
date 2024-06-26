package com.stew.kotlinbox.asyncthird

import com.xj.anchortask.library.AnchorTask
import com.xj.anchortask.library.IAnchorTaskCreator

/**
 * Created by stew on 2024/6/26.
 * mail: stewforani@gmail.com
 */
class ApplicationAnchorTaskCreator : IAnchorTaskCreator {
    override fun createTask(taskName: String): AnchorTask? {
        when (taskName) {
            ATConstants.TASK1 -> {
                return Task1()
            }
            ATConstants.TASK2 -> {
                return Task2()
            }
            ATConstants.TASK3 -> {
                return Task3()
            }
            ATConstants.TASK4 -> {
                return Task4()
            }
            ATConstants.TASK5 -> {
                return Task5()
            }
            ATConstants.TASK6 -> {
                return Task6()
            }
            ATConstants.TASK7 -> {
                return Task7()
            }
        }
        return null
    }

}