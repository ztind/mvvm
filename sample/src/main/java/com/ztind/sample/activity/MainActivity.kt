package com.ztind.sample.activity

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gyf.immersionbar.ImmersionBar
import com.ztind.mvvm.view.BaseActivity
import com.ztind.sample.R
import com.ztind.sample.adapter.MainVpAdapter
import com.ztind.sample.databinding.ActivityMainBinding

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
        /**
         * 未读消息
         */
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        //获取整个的NavigationView,添加未读消息ui
        val menuView: BottomNavigationMenuView = navView.getChildAt(0) as BottomNavigationMenuView
        for (i in 0..2){
            //这里就是获取所添加的每一个Tab(或者叫menu)，
            val tab: View = menuView.getChildAt(i)
            //这里就是获取所添加的每一个Tab(或者叫menu)，
            val itemView = tab as BottomNavigationItemView
            //加载我们的角标View，新创建的一个布局
            val badge = LayoutInflater.from(this).inflate(R.layout.menu_badge, menuView, false)
            val tx = badge.findViewById<TextView>(R.id.tv_msg_count)
            if(i==0){
                tx.setText("5")
            }else if(i==1){
                tx.setText("85")
            }else{
                tx.setText("99+")
            }
            //添加到Tab上
            itemView.addView(badge)
        }
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