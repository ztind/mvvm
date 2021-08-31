package com.ztind.sample.activity

import com.ztind.mvvm.view.BaseActivity
import com.ztind.sample.R
import com.ztind.sample.databinding.ActivityTwoBinding

/**
Describe：TwoActivity
Author:ZT
Date:2021/8/3
 */
class TwoActivity :BaseActivity<TwoViewModel,ActivityTwoBinding>(){
    /**
     * 本activity进入动画
     */
    override fun enterActivityAnim(): Int =  com.ztind.mvvm.R.anim.translate_down_to_between

    /**
     * 本activity退出动画
     */
    override fun exitActivityAnim(): Int = R.anim.translate_between_to_down

    /**
     * 开启仿微信侧滑退出
     */
    override fun isSupportSwipeBack(): Boolean = true

    /**
     * 设置状态栏颜色
     */
    override fun setStatusBarColor(): Int = R.color.color_9F74EC

    /**
     * 设置底部导航栏颜色
     */
    override fun setNavigationBarColor(): Int = R.color.color_9F74EC

    /**
     * 设置状态栏字体颜色为浅色(即白色)
     */
    override fun darkFont(): Boolean = false
}