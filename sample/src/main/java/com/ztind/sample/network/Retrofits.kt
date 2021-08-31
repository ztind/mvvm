package com.ztind.sample.network

import com.ztind.sample.utils.LoggerUtil
import com.ztind.mvvm.network.AbstractRetrofits
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit

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
     * 自定义拦截器
     */
    override fun httpInterceptor(): Interceptor? = LogIntercept2()
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
     * http请求-响应日志拦截器(1)
     */
    private class LogIntercept1 : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            LoggerUtil.v(message)
        }
    }
    /**
     * http请求-响应日志拦截器(2)
     */
    private class LogIntercept2 : Interceptor {
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