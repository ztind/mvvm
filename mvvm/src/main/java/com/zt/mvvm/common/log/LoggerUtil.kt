package com.zt.mvvm.common.log
import android.util.Log

/**
 * log日志工具小封装
 */
object LoggerUtil {
    const val DEBUG = true
    private val TAG = "hb"
    fun v(message :String){
        if (DEBUG)Log.v(TAG,message)
    }
    fun d(message :String){
        if(DEBUG)Log.d(TAG,message)
    }
    fun i(message :String){
        if (DEBUG)Log.i(TAG,message)
    }
    fun w(message :String){
        if (DEBUG)Log.w(TAG,message)
    }
    fun e(message :String){
        if (DEBUG)Log.e(TAG,message)
    }
}