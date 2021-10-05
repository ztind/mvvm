package com.ztind.sample.utils

import android.annotation.SuppressLint
import android.view.Gravity
import android.widget.Toast
import com.ztind.sample.App


/**
Describe：文件描述
Author:ZT
Date:2021/3/5
 */
object ToastUtils{
    private var toast:Toast?=null
    @SuppressLint("ShowToast")
    fun showShort(msg:String){
        if(toast==null){
           toast = Toast.makeText(App.instance.applicationContext,msg, Toast.LENGTH_SHORT)
        }
        toast?.run {
             setGravity(Gravity.CENTER,0,0)
             setText(msg)
             show()
        }
    }
    fun showLong(msg: String) {
        if(toast==null){
            toast = Toast.makeText(App.instance.applicationContext,msg, Toast.LENGTH_LONG)
        }
        toast?.run {
            setGravity(Gravity.CENTER,0,0)
            setText(msg)
            show()
        }
    }
}