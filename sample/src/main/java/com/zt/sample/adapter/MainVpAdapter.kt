package com.zt.sample.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zt.sample.fragment.DynamicFragment
import com.zt.sample.fragment.HomeFragment
import com.zt.sample.fragment.MessageFragment
import com.zt.sample.fragment.MineFragment
import java.util.ArrayList

/**
Viewpage2 API Change:
    1.FragmentStateAdapter 替换了FragmentStatePagerAdapter
    2.RecyclerView.Adapter 替换了 PagerAdapter
    3.registerOnPageChangeCallback 替换了 addPageChangeListener
 new function:
    1.支持RTL布局（Right-to-Left）UI布局的方式，中国用户很少使用）
    2.支持竖向滚动
    3.完整支持notifyDataSetChanged
 */
class MainVpAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragments = ArrayList<Fragment>()
    init {
        fragments.add(HomeFragment())
        fragments.add(DynamicFragment())
        fragments.add(MessageFragment())
        fragments.add(MineFragment())
    }
    override fun getItemCount(): Int  = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}