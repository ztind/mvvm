package com.zt.mvvm.common.utils

import android.util.Base64

/**
 * base64转化工具对象类
 */
object Base64Utils {
    fun getBase64(s :String):String{
        val bytes = s.toByteArray()
        return Base64.encodeToString(bytes, Base64.NO_WRAP or Base64.NO_PADDING or Base64.URL_SAFE)
    }
}