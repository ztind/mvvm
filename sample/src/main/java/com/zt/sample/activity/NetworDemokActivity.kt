package com.zt.sample.activity

import com.zt.mvvm.view.BaseActivity
import com.zt.sample.R
import com.zt.sample.databinding.ActivityNetworkDemoBinding

/**
Describe：网络请求demo Activity
Author:ZT
Date:2021/8/3
 */
class NetworDemokActivity :BaseActivity<NetworDemoViewModel,ActivityNetworkDemoBinding>(){
    override fun setStatusBarColor(): Int = R.color.colorPrimary
    override fun isSupportSwipeBack(): Boolean  = true
}