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
abstract class BaseFragmentA : Fragment(){
    //lateinit 则用于只能生命周期流程中进行获取或者初始化的变量，比如 Android 的 onCreate()
    lateinit var mContext : Context
    var rootView : View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(rootView==null){
            System.out.println("BaseFragmentA - onCreateView")
            rootView = inflater.inflate(setLayoutResId(),container,false)
            initView(rootView,savedInstanceState)
            initData()
        }
        return rootView
    }
    abstract fun setLayoutResId():Int

    abstract fun initView(rootView:View?,savedInstanceState: Bundle?)

    open fun initData(){}

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mContext = activity
    }
}