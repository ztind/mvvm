package com.zt.sample.activity

import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.zt.mvvm.common.utils.gotoActivity

/**
Describe：App启动页
Author:ZT
Date:2021/5/28
 */
class SplashActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).statusBarDarkFont(false).fullScreen(true).navigationBarColor(android.R.color.transparent).hideBar(
            BarHide.FLAG_HIDE_NAVIGATION_BAR).init()
        Handler().postDelayed({
            gotoActivity<MainActivity>(this)
            finish()
        },3000)
    }
    /**
     *防止在启动页时点击back返回键退出
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return true
    }
}