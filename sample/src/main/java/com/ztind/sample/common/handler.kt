package com.ztind.sample.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonParseException
import com.ztind.sample.utils.LoggerUtil
import com.ztind.mvvm.common.utils.State
import com.ztind.mvvm.common.utils.StateType
import com.ztind.sample.R
import com.ztind.sample.network.exception.BizException
import com.ztind.sample.beans.HttpResponse
import com.ztind.sample.beans.ResultCode
import org.apache.http.conn.ConnectTimeoutException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException

/**
Describe：公共处理工具
Author:ZT
Date:2021/8/3
 */
/**
 * 通用接口返回处理转化方法
 */
fun <T> CommonTransformHandler(response : HttpResponse<T>):T{
    return when(response.status){
        ResultCode.SUCCESS -> {
            response.data
        }
        else ->{
            val biz = BizException
            biz.status = response.status
            biz.msg = response.msg
            throw biz
        }
    }
}
/**
 * 公共异常处理对象类
 */
object CommonExceptionHandler {
    fun handler(e :Throwable, livedata : MutableLiveData<State>, context: Context, callBack:(state:State)->Unit){
        when(e){
            is BizException ->{
                LoggerUtil.e("BizException业务异常，${BizException.status}  ${BizException.msg}")
                /**
                 * 此处根据后端业务状态吗做相应判断处理即可
                 */
                when(BizException.status){
                    98 ->{

                    }
                    110 ->{

                    }
                    950 ->{

                    }
                    else->{

                    }
                }
            }
            is HttpException ->{
                when(e.code()){
                    400 -> {
                        LoggerUtil.e("HttpException 400:请求错误，参数校验错误")
                        val state = State(400,StateType.ERROR,"请求错误，参数校验错误${e.code()}")
                        livedata.postValue(state)
                        callBack(state)
                    }
                    401 -> {
                        LoggerUtil.e("HttpException 401:请求要求身份验证,token失效")
                        val state = State(400,StateType.ERROR,"请求要求身份验证,token失效${e.code()}")
                        livedata.postValue(state)
                        callBack(state)
                    }
                    404 -> {
                        LoggerUtil.e("HttpException 404:访问地址/资源不存在")
                        val state = State(404,StateType.ERROR,"访问地址/资源不存在${e.code()}")
                        livedata.postValue(state)
                        callBack(state)
                    }
                    426 -> {
                        LoggerUtil.e("HttpException 426:客户端应当切换到TLS/1.0")
                        val state = State(426,StateType.ERROR,"客户端应当切换到TLS/1.0 ${e.code()}")
                        livedata.postValue(state)
                        callBack(state)
                    }
                    500 -> {
                        LoggerUtil.e("HttpException 500")
                        val state = State(500,StateType.ERROR,"服务器内部错误${e.code()}")
                        livedata.postValue(state)
                        callBack(state)
                    }
                    else ->{
                        LoggerUtil.e("HttpException ${e.code()}")
                        val state = State(e.code(),StateType.ERROR,"服务器开小差了，请稍后再试 ${e.code()}")
                        livedata.postValue(state)
                        callBack(state)
                    }
                }
            }
            is SocketException ->{
                LoggerUtil.e("SocketException 手机无网络连接")
                val state = State(0,StateType.NETWOKR_RETRY,"暂无网络")
                livedata.postValue(state)
                callBack(state)
            }
            is ConnectException -> {
                //java.net.ConnectException: Failed to connect to /139.155.179.73:9999
                LoggerUtil.e("ConnectException 链接服务异常")
                val state = State(0,StateType.NETWOKR_RETRY,"链接服务异常")
                livedata.postValue(state)
                callBack(state)
            }
            is ConnectTimeoutException -> {
                LoggerUtil.e("ConnectTimeoutException 连接超时异常")
                val state = State(0,StateType.NETWOKR_RETRY,"链接超时异常")
                livedata.postValue(state)
                callBack(state)
            }
            is UnknownHostException -> {
                LoggerUtil.e("UnknownHostException 找不到主机异常")
                if(check_connect_net(context)){
                    val state = State(0,StateType.ERROR,"找不到主机异常")
                    livedata.postValue(state)
                    callBack(state)
                }else{
                    val state = State(0,StateType.NETWOKR_RETRY,  "暂无网络!")
                    livedata.postValue(state)
                    callBack(state)
                }
            }
            is JsonParseException -> {
                LoggerUtil.e("JsonParseException json解析异常")
                val state = State(0,StateType.ERROR,"json解析异常")
                livedata.postValue(state)
                callBack(state)
            }
            else->{
                LoggerUtil.e("其他异常 ${e.message} ")
                val state = State(0,StateType.ERROR,"服务器开小差了,请稍后再试")
                livedata.postValue(state)
                callBack(state)
            }
        }
    }
    fun handler(e :Throwable,livedata : MutableLiveData<State>,context:Context,retryCallBack:()->Unit,callBack:(state:State)->Unit){
        when(e){
            is BizException ->{
                LoggerUtil.e("BizException业务异常，${BizException.status}  ${BizException.msg}")
                /**
                 * 此处根据后端业务状态吗做相应判断处理即可
                 */
                when(BizException.status){
                    98 ->{

                    }
                    110 ->{

                    }
                    950 ->{

                    }
                    else->{

                    }
                }
            }
            is HttpException->{
                when(e.code()){
                    400 -> {
                        LoggerUtil.e("HttpException 400:请求错误，参数校验错误")
                        val state = State(400,StateType.ERROR,"请求错误，参数校验错误${e.code()}")
                        livedata.postValue(state)
                        callBack(state)
                    }
                    401 -> {
                        LoggerUtil.e("HttpException 401:请求要求身份验证,token失效")
                        val state = State(400,StateType.ERROR,"请求要求身份验证,token失效${e.code()}")
                        livedata.postValue(state)
                        callBack(state)
                    }
                    404 -> {
                        LoggerUtil.e("HttpException 404:访问地址/资源不存在")
                        val state = State(404,StateType.ERROR,"访问地址/资源不存在${e.code()}")
                        livedata.postValue(state)
                        callBack(state)
                    }
                    426 -> {
                        LoggerUtil.e("HttpException 426:客户端应当切换到TLS/1.0")
                        val state = State(426,StateType.ERROR,"客户端应当切换到TLS/1.0 ${e.code()}")
                        livedata.postValue(state)
                        callBack(state)
                    }
                    500 -> {
                        LoggerUtil.e("HttpException 500")
                        val view: View = View.inflate(context, R.layout.layout_state_server_error, null)
                        val state = State(500,StateType.ERROR,message = "服务器内部错误${e.code()}",stateView = view)
                        livedata.postValue(state)
                        callBack(state)
                    }
                    else ->{
                        LoggerUtil.e("HttpException ${e.code()}")
                        val state = State(e.code(),StateType.ERROR,"服务器开小差了，请稍后再试 ${e.code()}")
                        livedata.postValue(state)
                        callBack(state)
                    }
                }
            }
            is SocketException ->{
                LoggerUtil.e("SocketException 手机无网络连接")
                val view: View = View.inflate(context,R.layout.layout_state_network_error, null)
                view.findViewById<TextView>(R.id.retry).setOnClickListener { retryCallBack() }
                val state = State(0,StateType.NETWOKR_RETRY,message = "暂无网络", stateView = view)
                livedata.postValue(state)
                callBack(state)
            }
            is ConnectException -> {
                //java.net.ConnectException: Failed to connect to /139.155.179.73:9999
                LoggerUtil.e("ConnectException 链接服务异常")
                val view: View = View.inflate(context,R.layout.layout_state_network_error, null)
                view.findViewById<TextView>(R.id.retry).setOnClickListener { retryCallBack() }
                val state = State(0,StateType.NETWOKR_RETRY,message = "连接服务异常", stateView = view)
                livedata.postValue(state)
                callBack(state)
            }
            is ConnectTimeoutException -> {
                LoggerUtil.e("ConnectTimeoutException 连接超时异常")
                val view: View = View.inflate(context,R.layout.layout_state_network_timeout, null)
                view.findViewById<TextView>(R.id.retry).setOnClickListener { retryCallBack() }
                val state = State(0,StateType.NETWOKR_RETRY,message = "链接超时异常", stateView = view)
                livedata.postValue(state)
                callBack(state)
            }
            is UnknownHostException -> {
                LoggerUtil.e("UnknownHostException 找不到主机异常")
                if(check_connect_net(context)){
                    val view: View = View.inflate(context,R.layout.layout_state_unknowhost, null)
                    val state = State(0,StateType.ERROR,message = "找不到主机异常",stateView = view)
                    livedata.postValue(state)
                    callBack(state)
                }else{
                    val view: View = View.inflate(context,R.layout.layout_state_network_error, null)
                    view.findViewById<TextView>(R.id.retry).setOnClickListener { retryCallBack() }
                    val state = State(0,StateType.NETWOKR_RETRY,message = "暂无网络!!", stateView = view)
                    livedata.postValue(state)
                    callBack(state)
                }
            }
            is JsonParseException -> {
                LoggerUtil.e("JsonParseException json解析异常")
                val view: View = View.inflate(context,R.layout.layout_state_jsonparse_exception, null)
                val state = State(0,StateType.ERROR,message = "json解析异常",stateView = view)
                livedata.postValue(state)
                callBack(state)
            }
            else->{
                LoggerUtil.e("其他异常 ${e.message} ")
                val state = State(0,StateType.ERROR,message = "服务器开小差了,请稍后再试")
                livedata.postValue(state)
                callBack(state)
            }
        }
    }

    /**
     * 检查是否能连接网络
     */
    @SuppressLint("ServiceCast")
    private fun check_connect_net(con: Context): Boolean {
        val cwjManager = con.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            if (cwjManager.activeNetworkInfo != null && cwjManager.activeNetworkInfo!!.isAvailable) {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.activeNetworkInfo
        if (info != null && info.isConnected) {
            // 当前网络是连接的
            // 当前所连接的网络可用
            return info.state == NetworkInfo.State.CONNECTED
        }
        return false
    }
}