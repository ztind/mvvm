package com.zt.mvvm.network.exception

/**
 * 无数据异常
 */
object EmptyException : RuntimeException() {
    /**
     * 返回消息
     */
    var msg: String? = null
}