package com.ztind.sample.fragment

import com.kongzue.dialogx.dialogs.MessageDialog
import com.ztind.mvvm.common.utils.gotoActivity
import com.ztind.mvvm.viewmodel.BaseViewModel
import com.ztind.sample.activity.NetworkDemokActivity
import com.ztind.sample.activity.TwoActivity
import com.ztind.sample.databinding.FragmentHomeBinding

/**
Describe：文件描述
Author:ZT
Date:2021/7/21
 */
class HomeViewModel :BaseViewModel<HomeRepository,FragmentHomeBinding>() {
    private val mFragment by lazy {
        mLifecycleOwner  as HomeFragment
    }
    override fun initView() {
        mBinding.viewmodel = this
    }

    override fun initData() {

    }

    fun goAty(){
        gotoActivity<TwoActivity>(mFragment.mContext)
    }
    fun goNetworkDemoAty(){
        gotoActivity<NetworkDemokActivity>(mFragment.mContext)
    }
    fun iosDialog(){
        MessageDialog.show("标题", "正文内容", "确定", "取消")
    }
}