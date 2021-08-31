package com.ztind.sample.network.exception

/**
 * 业务异常
 */
object BizException : RuntimeException() {
    /**
     * 返回消息
     */
    var msg: String? = null
    /**
     * 状态码
     */
    var status:Int = 0
}