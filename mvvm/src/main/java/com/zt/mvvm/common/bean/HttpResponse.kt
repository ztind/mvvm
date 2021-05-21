package com.zt.mvvm.common.bean

import androidx.lifecycle.MutableLiveData
import com.zt.libandroid.constant.ResultCode
import com.zt.mvvm.common.utils.State
import com.zt.mvvm.common.utils.StateType
import retrofit2.http.HTTP

/**
服务端接口公共返回内容
 */
data class HttpResponse<T>(
    /**
     * 接口返回code
     * 200 成功
     */
    val code : Int,
    /**
     * 接口返回消息
     */
    val msg : String,
    /**
     * 接口返回数据
     */
    val data : T
)

//fun <T> HttpResponseBean<T>.paras(
//    loadState: MutableLiveData<State>
//) :T{
//        when(code){
//            ResultCode.SUCCESS ->{
//                if (data is List<*>) {
//                    if ((data as List<*>).isEmpty()) {
//                        loadState.postValue(State(200,StateType.EMPTY))
//                    }
//                }
//                loadState.postValue(State(200,StateType.SUCCESS))
//                return data
//            }
//            ResultCode.FAIL -> {
//                loadState.postValue(State(200,StateType.ERROR, message = msg))//通知数据状态改变，导致Observer方法回调，更新ui或其他逻辑
//                return data
//            }
//            ResultCode.NO_AUTHOR ->{
//                //弹框提示重新登陆/清除本地登陆信息/其他逻辑
//                loadState.postValue(State(200,StateType.ERROR, message = "请重新登录"))
//                return data
//            }
//            else ->{
//                loadState.postValue(State(200,StateType.ERROR, message = msg))
//                return data
//            }
//        }
//}
