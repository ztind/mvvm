package com.ztind.mvvm.common.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*
import kotlin.system.exitProcess

/**
 * **Title: **应用程序Activity管理类：用于Activity管理和应用程序退出
 * **Description: **这里写类的详细描述
 */
object AppManager {
    private val activityStack : Stack<Activity> = Stack()

    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activityStack.remove(activity)
    }

    fun finishAllActivity() {
        for(activity in activityStack) {
            activity.finish()
        }
        activityStack.clear()
    }

    fun exitApp(context: Context) {
        finishAllActivity()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        exitProcess(0)
    }
}