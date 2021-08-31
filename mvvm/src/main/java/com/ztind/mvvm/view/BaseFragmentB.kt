package com.ztind.mvvm.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Fragment基类
 */
abstract class BaseFragmentB : Fragment(){
    //lateinit 则用于只能生命周期流程中进行获取或者初始化的变量，比如 Android 的 onCreate()
    lateinit var mContext : Context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(setLayoutResId(),container,false)
    }
    abstract fun setLayoutResId():Int

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mContext = activity
    }
}