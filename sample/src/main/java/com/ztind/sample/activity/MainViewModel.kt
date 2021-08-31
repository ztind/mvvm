package com.ztind.sample.activity

import com.ztind.mvvm.viewmodel.BaseViewModel
import com.ztind.sample.databinding.ActivityMainBinding

/**
Describe：文件描述
Author:ZT
Date:2021/5/28
 */
class MainViewModel :BaseViewModel<MainRepository,ActivityMainBinding>(){
    private val mActivity by lazy {
        mLifecycleOwner as MainActivity
    }
    override fun initView() {

    }

    override fun initData() {

    }
}