package com.zt.mvvm.network

import okhttp3.Interceptor
import retrofit2.Retrofit

/**
 * 对RetrofitFactory的进行服务层的封装
 */
abstract class AbstractRetrofits {
    /**
     * base url
     */
    abstract fun baseUrl(): String
    /**
     * token key
     */
    abstract fun tokenKey():String
    /**
     * token
     */
    abstract fun tokenValue():String
    /**
     * 请求头参数
     */
    abstract fun headers():HashMap<String,String>
    /**
     * http请求拦截器
     */
    abstract fun httpInterceptor(): Interceptor?
    /**
     * 链接超时(默认10秒)
     */
    open fun connectTime():Long = 10

    /**
     * 读取超时(默认10秒)
     */
    open fun readTime():Long = 10

    /**
     * 写超时(默认10秒)
     */
    open fun writeTime():Long = 10
    /**
     * 获取认证的实例
     */
    fun authorizedService(): Retrofit{
        return  RetrofitManage.getRetrofitFactoryInstance().getAuthorizedRetrofit(baseUrl(),headers(),connectTime(),readTime(),writeTime(),tokenKey(),httpInterceptor()) {
            tokenValue()
        }
    }
    /**
     * 获取未认证的实例
     */
    fun unauthorizedService():Retrofit{
        return RetrofitManage.getRetrofitFactoryInstance().getUnauthorizedRetrofit(baseUrl(),headers(),connectTime(),readTime(),writeTime(),tokenKey(),httpInterceptor())
    }
}