package com.ztind.sample.beans

/**
Describe：数据bean定义
Author:ZT
Date:2021/8/3
 */

/**
 * 妹纸数据bean
 */
data class MeiZi (
    var _id: String,
    val author: String,
    val desc: String,
    val images:List<String>,
    val url: String,
    val title:String,
    val views: Int
)
