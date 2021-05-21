package com.zt.mvvm.common.utils
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.zt.libandroid.constant.ResultCode
import com.zt.mvvm.common.bean.HttpResponse
import com.zt.mvvm.network.exception.BizException
/**
 * 全局activity跳转函数
 * inline 内联关键字 函数体大/多次引用调用
 * reified 具体化的修饰一个具体的参数
 */
inline fun <reified T : AppCompatActivity> gotoActivity(context : Context){
    val intent = Intent()
    intent.setClass(context,T::class.java)
    context.startActivity(intent)
}
inline fun <reified T : AppCompatActivity> gotoActivity(context : Context,block: Intent.()->Unit){
    val intent = Intent()
    intent.setClass(context,T::class.java)
    intent.block()
    context.startActivity(intent)
}
inline fun <reified T : AppCompatActivity> gotoActivityForResult(activity : AppCompatActivity,requestCode:Int){
    val intent = Intent()
    intent.setClass(activity,T::class.java)
    activity.startActivityForResult(intent,requestCode)
}
inline fun <reified T : AppCompatActivity> gotoActivityForResult(activity : AppCompatActivity,requestCode:Int,block: Intent.()->Unit){
    val intent = Intent()
    intent.setClass(activity,T::class.java)
    intent.block()
    activity.startActivityForResult(intent,requestCode)
}

/**
 * 通用接口返回处理转化方法
 */
fun <T> CommonTransformHandler(response : HttpResponse<T>):T{
    return when(response.code){
        ResultCode.SUCCESS -> {
            if (response.data is List<*>) {
                if ((response.data as List<*>).isEmpty()) {
                    //loadState.postValue(State(StateType.EMPTY))
                }
            }
            //loadState.postValue(State(StateType.SUCCESS))
            response.data
        }
        else ->{
            val biz = BizException
            biz.code = response.code
            biz.msg = response.msg
            throw biz
        }
    }
}