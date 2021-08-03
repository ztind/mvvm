package com.zt.sample

import android.app.Application
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper

/**
Describe：文件描述
Author:ZT
Date:2021/5/28
 */
class App :Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化侧滑库
        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null)
    }
    init {
        instance = this
    }
    companion object {
        lateinit var instance: App
    }
}