package com.ztind.sample.network.api

import com.ztind.sample.beans.HttpResponse
import com.ztind.sample.beans.MeiZi
import retrofit2.http.GET
import retrofit2.http.Path

/**
Describe：妹纸数据api
Author:ZT
Date:2021/8/3
 */
interface MeiZiAPI {
    /**
     * 获取妹纸数据，数据来源gank.io
     */
    @GET("api/v2/data/category/Girl/type/Girl/page/{page}/count/{size}")
    suspend fun getData(@Path("page") page: Int, @Path("size") size: Int) : HttpResponse<MutableList<MeiZi>?>
}