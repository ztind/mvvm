package com.zt.sample.beans

/**
Describe：公共数据bean
Author:ZT
Date:2021/8/3
 */

/**
服务端api接口数据统一解析bean
 */
data class HttpResponse<T>(
    /**
     * 返回码
     */
    val status : Int,
    /**
     * 消息
     */
    val msg : String,
    /**
     * 数据
     */
    val data : T
)
/**
 * api接口返回状态码
 */
object ResultCode {
    /**
     * 标记成功
     */
    val SUCCESS :Int = 100
    /**
     * 标记失败
     */
    val  FAIL :Int = 0
}
