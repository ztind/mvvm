package com.zt.mvvm.network

import retrofit2.Retrofit

/**
 * 对RetrofitFactory的进行服务层的封装
 */
abstract class AbstractRetrofits {

    abstract fun baseUrl(): String
    abstract fun token():String
    abstract fun version():String
    abstract fun headers():HashMap<String,String>
    abstract fun clientId():String
    abstract fun clientSec():String
    /**
     * 获取认证的实例
     */
    fun authorizedService(): Retrofit{
        return  RetrofitManage.getRetrofitFactoryInstance().getAuthorizedRetrofit(baseUrl(),headers(),{
            version()
        }){
            token()
        }
    }

    /**
     * 获取未认证的实例
     */
    fun unauthorizedService():Retrofit{
        return RetrofitManage.getRetrofitFactoryInstance().getUnauthorizedRetrofit(baseUrl(),headers(),clientId(),clientSec()){
            version()
        }
    }
}