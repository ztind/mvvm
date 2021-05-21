package com.zt.mvvm.common.utils

import android.view.View
import androidx.annotation.LayoutRes
import com.zt.mvvm.R
import java.lang.reflect.ParameterizedType

object CommonUtil {
    /**
     * 获取t对象父类泛型(T)类第一个参数的class类对象
     */
    fun <T> getClass(t: Any): Class<T> {
        // 通过反射 获取父类泛型 (T) 对应 Class类
        return (t.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
    }
    /**
     * 判断传入的文本是否为空
     */
    fun isEmpty(txt: String?): Boolean {
        return txt == null || txt == "" || txt.isEmpty() || txt.toString().trim().isEmpty()
    }
}
/**
 * httpStatus: http响应状态码
 * code: api接口状态码
 * stateType：状态类型
 * message:消息
 * stateView:多状态布局view
 */
data class State(var httpStatus :Int = 200,var state : StateType = StateType.SUCCESS, var message: String = "",var code:Int=0,var stateView:View?=null)
/**
 * 应用状态
 */
enum class StateType {
    SUCCESS,
    ERROR,
    EMPTY,
    LOADING,
    NETWOKR_RETRY//网络重试
}

/**
 * 判断传入的文本是否为空
 */
fun isEmpty(txt: String?): Boolean {
    return txt == null || txt == "" || txt.isEmpty() || txt.toString().trim().isEmpty()
}