package com.zt.sample.activity

import com.zt.mvvm.repository.BaseRepository
import com.zt.sample.beans.HttpResponse
import com.zt.sample.beans.MeiZi
import com.zt.sample.network.Retrofits
import com.zt.sample.network.api.MeiZiAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
Describe：文件描述
Author:ZT
Date:2021/8/3
 */
class NetworDemoRepository :BaseRepository{
    private val meiziAPI = Retrofits.getRetrofitsInstance().unauthorizedService().create(MeiZiAPI::class.java)
    /**
     * 获取妹纸数据
     */
    suspend fun getData(page:Int,size:Int):Flow<HttpResponse<MutableList<MeiZi>?>>{
        return flow {
            val data = meiziAPI.getData(page,size)
            emit(data)
        }.flowOn(Dispatchers.IO)
    }
}