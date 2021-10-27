package com.ztind.sample.utils

import android.content.Context
import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
Describe：tencent mmkv 封装
Author:ZT
Date:2021/10/28
 */
object MMKVUtils {
    /**
     * Application中调用初始化mmkv
     */
    private var kv:MMKV? = null
    fun init(context: Context){
        MMKV.initialize(context)
        kv = MMKV.defaultMMKV()
    }

    /**
     * 通过key存储value
     */
    fun encode(key:String,any: Any){
        kv?.let {
            when (any) {
                is String -> {
                    it.encode(key, any)
                }
                is Int -> {
                    it.encode(key, any)
                }
                is Double ->{
                    it.encode(key,any)
                }
                is Float ->{
                    it.encode(key,any)
                }
                is Boolean ->{
                    it.encode(key,any)
                }
                is Long ->{
                    it.encode(key,any)
                }
                is ByteArray ->{
                    it.encode(key,any)
                }
                else -> {
                    it.encode(key,any.toString())
                }
            }
        }
    }
    fun encodeSet(key: String,sets: Set<String>){
        kv?.encode(key, sets)
    }
    fun encodeParcelable(key: String, obj: Parcelable) {
        kv?.encode(key, obj)
    }

    /**
     * 通过key获取value
     */
    fun decodeInt(key: String): Int? = kv?.decodeInt(key,0)

    fun decodeDouble(key: String): Double? = kv?.decodeDouble(key, 0.00)

    fun decodeLong(key: String): Long? = kv?.decodeLong(key, 0L)

    fun decodeBoolean(key: String): Boolean? = kv?.decodeBool(key, false)

    fun decodeFloat(key: String): Float? = kv?.decodeFloat(key, 0f)

    fun decodeBytes(key: String): ByteArray? = kv?.decodeBytes(key)

    fun decodeString(key: String): String? = kv?.decodeString(key, "")

    fun decodeStringSet(key: String): Set<String?>? = kv?.decodeStringSet(key, emptySet<String>())

    fun decodeParcelable(key: String): Parcelable? = kv?.decodeParcelable(key, null)

    /**
     * 移除某个key对
     */
    fun removeKey(key: String) {
        kv?.removeValueForKey(key)
    }
    /**
     * 清除所有key
     */
    fun clearAll() {
        kv?.clearAll()
    }

}