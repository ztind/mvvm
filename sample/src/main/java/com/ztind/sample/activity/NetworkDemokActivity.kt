package com.ztind.sample.activity

import com.ztind.mvvm.view.BaseActivity
import com.ztind.sample.R
import com.ztind.sample.databinding.ActivityNetworkDemoBinding

/**
Describe：网络请求demo Activity
Author:ZT
Date:2021/8/3
 */
class NetworkDemokActivity :BaseActivity<NetworkDemoViewModel,ActivityNetworkDemoBinding>(){
    override fun setStatusBarColor(): Int = R.color.colorPrimary
    override fun isSupportSwipeBack(): Boolean  = true
}