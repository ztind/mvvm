package com.zt.mvvm.network.exception

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
    var code:Int = 0
}