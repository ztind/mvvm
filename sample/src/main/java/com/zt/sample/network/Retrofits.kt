package com.zt.sample.network

import com.zt.mvvm.network.AbstractRetrofits

/**
Describe：网络请求
Author:朱彤
Date:2021/5/29
 */
class Retrofits : AbstractRetrofits() {
    /**
     * base url
     */
    override fun baseUrl(): String  = "https://www.baidu.com"

    /**
     * token
     */
    override fun token(): String  = "you token"

    /**
     * 请求头参数:app版本名称
     */
    override fun version(): String = "1.0.0"

    /**
     * 请求头参数,可自定义添加
     */
    override fun headers(): HashMap<String, String> {
        val map = HashMap<String,String>()
        map["brand"] = android.os.Build.BRAND
        map["model"] = android.os.Build.MODEL
        map["release"] = android.os.Build.VERSION.RELEASE
        return map
    }

    override fun clientId(): String = "sample"

    override fun clientSec(): String = "sample"

    /**
     * 类实例获取
     */
    companion object{
        var instance : Retrofits?=null
            get() {
                if (field==null){
                    field = Retrofits()
                }
                return field
            }
        @Synchronized
        fun getRetrofitsInstance(): Retrofits {
            return instance!!
        }
    }
}