package com.zt.sample.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.zt.mvvm.view.BaseActivity
import com.zt.sample.R
import com.zt.sample.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>() {
    override fun setLayoutResId(): Int = R.layout.activity_main
    override fun initView() {
        super.initView()
        //取消icon的默认着色
        mDataBinding.bottomNavView.itemIconTintList = null
        /**
         * 去除BottomNavigationView长按Toast通知
         */
        mDataBinding.bottomNavView.findViewById<BottomNavigationItemView>(R.id.navigation_home).setOnLongClickListener { true }
        mDataBinding.bottomNavView.findViewById<BottomNavigationItemView>(R.id.navigation_dynamic).setOnLongClickListener { true }
        mDataBinding.bottomNavView.findViewById<BottomNavigationItemView>(R.id.navigation_message).setOnLongClickListener { true }
        mDataBinding.bottomNavView.findViewById<BottomNavigationItemView>(R.id.navigation_mine).setOnLongClickListener { true }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}