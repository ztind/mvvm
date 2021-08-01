package com.zt.mvvm.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.zt.mvvm.common.log.LoggerUtil
import com.zt.mvvm.common.utils.Base64Utils
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * 提供Retrofit网络请求封装
 * https问题https://blog.csdn.net/lmj623565791/article/details/48129405
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
        val instance: RetrofitManage = RetrofitManage()
        //在Kotlin中，如果你需要将方法声明为同步，需要添加@Synchronized注解。
        @Synchronized
        fun getRetrofitFactoryInstance(): RetrofitManage {
            return instance
        }
    }

    /**
     * 需授权身份验证的api操作
     * @param baseUrl   指定的URL
     * @param token     身份标识token
     * @param headers   请求头参数
     * @return {@link Retrofit}对象
     */
    fun getAuthorizedRetrofit(baseUrl: String, headers: HashMap<String, String>,version:()->String,getTokenCallBack:()->String): Retrofit {
        if(authorizedClient==null){
            authorizedClient = clientBuilder
                .connectTimeout(10, TimeUnit.SECONDS)
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
                //FaceBook 网络调试器，可在Chrome调试网络请求，查看SharePreferences,数据库等
                .addNetworkInterceptor(StethoInterceptor())
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val requestBuilder = chain.request()
                            .newBuilder()
                            .header("version", version())
                            .header("Authorization", getTokenCallBack())
                            .header("Accept", "*/*")
                        headers.forEach { requestBuilder.header(it.key, it.value) }
                        return chain.proceed(requestBuilder.build())
                    }
                })
                //http数据log，日志中打印出HTTP请求&响应数据
                .addInterceptor(HttpLoggingInterceptor(HttpLog()).setLevel(HttpLoggingInterceptor.Level.BODY))
                //.addInterceptor(LogIntercept())
                .build()
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
     * @param clientId  客户端ID
     * @param clientSec 客户端密钥
     * @return {@link Retrofit}对象
     */
    fun getUnauthorizedRetrofit(baseUrl: String, headers: HashMap<String, String>, clientId: String, clientSec: String,version:()->String): Retrofit {
        val encoded = Base64Utils.getBase64("$clientId:$clientSec")
        if(unauthorizedClient==null){
            unauthorizedClient  = clientBuilder
                .connectTimeout(10, TimeUnit.SECONDS)
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
                //FaceBook 网络调试器，可在Chrome调试网络请求，查看SharePreferences,数据库等
                .addNetworkInterceptor(StethoInterceptor())
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val requestBuilder = chain.request()
                            .newBuilder()
                            .header("version", version())
                            .header("Authorization", "Basic $encoded")
                            .header("Accept", "*/*")
                        headers.forEach { requestBuilder.header(it.key, it.value) }
                        return chain.proceed(requestBuilder.build())
                    }
                })
                //http数据log，日志中打印出HTTP请求&响应数据
                .addInterceptor(HttpLoggingInterceptor(HttpLog()).setLevel(HttpLoggingInterceptor.Level.BODY))
                //.addInterceptor(LogIntercept())
                .build()
        }
        return retrofitBuilder
            .baseUrl(baseUrl)
            .client(unauthorizedClient!!)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    /**
     * http请求日志拦截器
     */
    private class HttpLog : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            LoggerUtil.v(message)
        }
    }

    /**
     * http请求-响应日志拦截器
     */
    private class LogIntercept : Interceptor {
        private val UTF8 = Charset.forName("UTF-8")

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val requestBody = request.body
            val hasRequestBody = requestBody != null
            val connection = chain.connection()
            val protocol = connection?.protocol() ?: Protocol.HTTP_1_1
            LoggerUtil.v("""
    start request
    request method = [${request.method}]
    request url = [${request.url}]
    request protocol = [$protocol]
    request.body = [$request]
    """.trimIndent())
            if (hasRequestBody) {
                if (requestBody!!.contentType() != null) {
                    LoggerUtil.v("request content type = [" + requestBody.contentType() + "]")
                }
            }
            val headers = request.headers
            val sBuilder = StringBuilder("Headers:\n")
            var i = 0
            val count = headers.size
            while (i < count) {
                val name = headers.name(i)
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(name, ignoreCase = true)) {
                    sBuilder.append(name)
                    sBuilder.append(" : ")
                    sBuilder.append(headers.value(i))
                    sBuilder.append("\n")
                }
                i++
            }
            LoggerUtil.v("request string builder = [$sBuilder]")
            val startNs = System.nanoTime()
            val response: Response
            response = try {
                chain.proceed(request)
            } catch (e: Exception) {
                LoggerUtil.e("request failed, throw error = [" + e.message + "]")
                throw e
            }
            val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
            val responseBody = response.body
            val contentLength = responseBody!!.contentLength()
            val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"
            val bodyResponse = """
                Response Code = [${response.code}];
                Response Size = [$bodySize];
                Took Request Time = [${tookMs}ms]
                """.trimIndent()
            LoggerUtil.v(bodyResponse)
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            var charset = UTF8
            val contentType = responseBody.contentType()
            if (contentType != null) {
                charset = try {
                    contentType.charset(UTF8)
                } catch (e: UnsupportedCharsetException) {
                    LoggerUtil.e("end request, couldn't decode the response body, charset is likely "
                            + "malformed.")
                    return response
                }
            }
            if (!isPlaintext(buffer)) {
                LoggerUtil.v("end request, the binary = [" + buffer.size + "] byte body omitted)")
                return response
            }
            if (contentLength != 0L) {
                LoggerUtil.v("request result = " + buffer.clone().readString(charset!!))
            }
            LoggerUtil.v("""
    the binary = [${buffer.size}] byte body
    end request
    """.trimIndent())
            return response
        }

        private fun isPlaintext(buffer: Buffer): Boolean {
            return try {
                val prefix = Buffer()
                val byteCount = if (buffer.size < 64) buffer.size else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                true
            } catch (e: EOFException) {
                false // Truncated UTF-8 sequence.
            }
        }
    }
}