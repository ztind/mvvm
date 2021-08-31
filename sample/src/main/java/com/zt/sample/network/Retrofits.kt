package com.zt.sample.network

import com.zt.mvvm.network.AbstractRetrofits

/**
Describe：网络请求
Author:ZT
Date:2021/5/29
 */
class Retrofits : AbstractRetrofits() {
    /**
     * base url
     */
    override fun baseUrl(): String  = "https://gank.io/"

    /**
     * you token key
     */
    override fun tokenKey(): String = "Authorization"

    /**
     * you token value
     */
    override fun tokenValue(): String = "token"

    /**
     * 请求头参数,可自定义添加
     */
    override fun headers(): HashMap<String, String> {
        val map = HashMap<String,String>()
        //手机厂商
        map["brand"] = android.os.Build.BRAND
        //手机型号
        map["model"] = android.os.Build.MODEL
        //手机系统版本
        map["release"] = android.os.Build.VERSION.RELEASE
        //app版本
        map["version"] = "1.0.0"
        return map
    }

    /**
     * 类实例获取
     */
    companion object{
        private val instance : Retrofits = Retrofits()
        @Synchronized
        fun getRetrofitsInstance(): Retrofits {
            return instance
        }
    }

    /**
     * 链接超时(秒)
     */
    override fun connectTime(): Long = 10
    /**
     * 读取超时(秒)
     */
    override fun readTime(): Long = 10
    /**
     * 写超时(秒)
     */
    override fun writeTime(): Long = 10
}