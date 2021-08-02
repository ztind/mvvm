package com.zt.sample.fragment

import com.zt.mvvm.common.utils.gotoActivity
import com.zt.mvvm.viewmodel.BaseViewModel
import com.zt.sample.activity.TwoActivity
import com.zt.sample.databinding.FragmentHomeBinding

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
}