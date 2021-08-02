package com.zt.sample.activity

import android.view.KeyEvent
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.gyf.immersionbar.ImmersionBar
import com.zt.mvvm.common.utils.gotoActivity
import com.zt.mvvm.view.BaseActivity
import com.zt.sample.R
import com.zt.sample.adapter.MainVpAdapter
import com.zt.sample.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>() {
    override fun initView() {
        super.initView()
        initVpBottomNav()
    }
    fun initVpBottomNav(){
        mDataBinding.bottomNavView.itemIconTintList = null //取消icon的默认着色
        mDataBinding.vp.adapter =  MainVpAdapter(this)
        var barDarkFont = true //状态栏字体是否深色
        mDataBinding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                var fragment_id: Int = R.id.navigation_home
                when (position) {
                    0 -> {
                        fragment_id = R.id.navigation_home
                        barDarkFont = true
                    }
                    1 -> {
                        fragment_id = R.id.navigation_dynamic
                        barDarkFont = false
                    }
                    2 -> {
                        fragment_id = R.id.navigation_message
                        barDarkFont = true
                    }
                    3 -> {
                        fragment_id = R.id.navigation_mine
                        barDarkFont = false
                    }
                }
                mDataBinding.bottomNavView.selectedItemId = fragment_id
                ImmersionBar.with(this@MainActivity).statusBarDarkFont(barDarkFont).init()
            }
        })
        val smoothScroll = true //点击tab,vp切换是否显示滑动效果
        mDataBinding.bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    mDataBinding.vp.setCurrentItem(0, smoothScroll)
                    barDarkFont = true
                }
                R.id.navigation_dynamic -> {
                    mDataBinding.vp.setCurrentItem(1, smoothScroll)
                    barDarkFont = false
                }
                R.id.navigation_message ->{
                    mDataBinding.vp.setCurrentItem(2, smoothScroll)
                    barDarkFont = true
                }
                R.id.navigation_mine ->{
                    mDataBinding.vp.setCurrentItem(3, smoothScroll)
                    barDarkFont = false
                }
            }
            ImmersionBar.with(this).statusBarDarkFont(barDarkFont).init()
            true
        }
        /**
         * 去除BottomNavigationView长按Toast通知
         */
        mDataBinding.bottomNavView.findViewById<BottomNavigationItemView>(R.id.navigation_home).setOnLongClickListener {true}
        mDataBinding.bottomNavView.findViewById<BottomNavigationItemView>(R.id.navigation_dynamic).setOnLongClickListener { true }
        mDataBinding.bottomNavView.findViewById<BottomNavigationItemView>(R.id.navigation_message).setOnLongClickListener { true }
        mDataBinding.bottomNavView.findViewById<BottomNavigationItemView>(R.id.navigation_mine).setOnLongClickListener { true }
    }

    /**
     * 自定义沉浸式状态栏
     */
    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .navigationBarColor(R.color.color_navigation)
            .init()
    }

    /**
     * activity进入动画（淡入缩放）
     */
    override fun enterActivityAnim(): Int = R.anim.anim_scale

    /**
     * 点击back应用退到系统后台
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}