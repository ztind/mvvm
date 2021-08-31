package com.ztind.mvvm.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * 提供Retrofit网络请求封装
 */
class RetrofitManage private constructor() {

    private var authorizedClient: OkHttpClient? = null
    private var unauthorizedClient: OkHttpClient? = null

    private val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()

    /**
     * 线程安全的单列懒汉式
     */
    companion object {
        private val instance: RetrofitManage = RetrofitManage()
        //在Kotlin中，如果你需要将方法声明为同步，需要添加@Synchronized注解。
        @Synchronized
        fun getRetrofitFactoryInstance(): RetrofitManage {
            return instance
        }
    }

    /**
     * 需授权身份验证的api操作
     * @param baseUrl   指定的URL
     * @param headers   请求头参数
     * @param connect_time   链接超时(秒)
     * @param read_time   读取超时(秒)
     * @param write_time   写超时(秒)
     * @param tokenKey   token key
     * @param httpInterceptor 拦截器
     * @param tokenValue     身份标识token,返回token
     * @return {@link Retrofit}对象
     */
    fun getAuthorizedRetrofit(baseUrl: String, headers: HashMap<String, String>,connect_time:Long,read_time:Long,write_time:Long,tokenKey:String,httpInterceptor:Interceptor?,tokenValue:()->String): Retrofit {
        if(authorizedClient==null){
            clientBuilder
                .connectTimeout(connect_time, TimeUnit.SECONDS)
                .readTimeout(read_time,TimeUnit.SECONDS)
                .writeTimeout(write_time,TimeUnit.SECONDS)
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
                //FaceBook 网络调试器，可在Chrome调试网络请求，查看SharePreferences,数据库等
                .addNetworkInterceptor(StethoInterceptor())
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val requestBuilder = chain.request().newBuilder().header("Accept","*/*")
                        headers.forEach {
                            requestBuilder.header(it.key, it.value)
                        }
                        requestBuilder.header(tokenKey, tokenValue())
                        return chain.proceed(requestBuilder.build())
                    }
                })
                //http数据log，日志中打印出HTTP请求&响应数据
                if(httpInterceptor!=null){
                    clientBuilder.addInterceptor(httpInterceptor)
                }
            authorizedClient = clientBuilder.build()
        }
        return retrofitBuilder
            .baseUrl(baseUrl)
            .client(authorizedClient!!)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
    /**
     * 无需身份验证的api操作
     * @param baseUrl   指定的URL
     * @param headers   请求头参数
     * @param connect_time   链接超时(秒)
     * @param read_time   读取超时(秒)
     * @param write_time   写超时(秒)
     * @param tokenKey   token key
     * @param httpInterceptor   拦截器
     * @return {@link Retrofit}对象
     */
    fun getUnauthorizedRetrofit(baseUrl: String, headers: HashMap<String, String>,connect_time:Long,read_time:Long,write_time:Long,tokenKey:String,httpInterceptor:Interceptor?): Retrofit {
        if(unauthorizedClient==null){
            clientBuilder
                .connectTimeout(connect_time, TimeUnit.SECONDS)
                .readTimeout(read_time,TimeUnit.SECONDS)
                .writeTimeout(write_time,TimeUnit.SECONDS)
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
                //FaceBook 网络调试器，可在Chrome调试网络请求，查看SharePreferences,数据库等
                .addNetworkInterceptor(StethoInterceptor())
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val requestBuilder = chain.request().newBuilder()
                                .header("Accept","*/*")
                        headers.forEach {
                                if(it.key != tokenKey){
                                    requestBuilder.header(it.key, it.value)
                                }
                        }
                        return chain.proceed(requestBuilder.build())
                    }
                })
                //http数据log，日志中打印出HTTP请求&响应数据
                if(httpInterceptor!=null){
                    clientBuilder.addInterceptor(httpInterceptor)
                }
            unauthorizedClient = clientBuilder.build()
        }
        return retrofitBuilder
            .baseUrl(baseUrl)
            .client(unauthorizedClient!!)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
}