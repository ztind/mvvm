package com.ztind.mvvm.common.utils
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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