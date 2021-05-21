package com.zt.mvvm.view


import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import com.gyf.immersionbar.ImmersionBar
import com.zt.mvvm.R
import com.zt.mvvm.common.utils.AppManager
import com.zt.mvvm.common.utils.CommonUtil
import com.zt.mvvm.viewmodel.BaseViewModel

/**
 * Desc:Activity基类
 * Author:朱彤
 * Date:2021/1/6
 */
abstract  class BaseActivity<VM : BaseViewModel<*,DB>,DB : ViewDataBinding>: AppCompatActivity(),BGASwipeBackHelper.Delegate{
    //父类里实例化ViewModel
    lateinit var mViewModel : VM
    //databinding
    protected lateinit var mDataBinding : DB
    protected lateinit var mSwipeBackHelper: BGASwipeBackHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        initSwipeBackFinish()
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, setLayoutResId())
        mViewModel = ViewModelProvider(this).get(CommonUtil.getClass<VM>(this))
        mViewModel.mBinding = mDataBinding
        mDataBinding.lifecycleOwner = this
        initImmersionBar()
        enterActivityAnim(R.anim.translate_right_to_between,R.anim.translate_none)
        initView()
        mViewModel.gotoInit()
        AppManager.addActivity(this)
    }
    /**
     * 子类实现返回布局文件id
     */
    abstract fun setLayoutResId() : Int
    /**
     * 初始化视图
     */
    protected open fun initView(){
        /**
         * 获取根布局，统一设置白色背景，解决activity引入侧滑库时的背景全色问题
         */
        val rootView = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
        val drawable = rootView.background
        //若根布局未自定义background，则进行设置根布局背景颜色
        if (drawable == null) rootView.setBackgroundColor(resources.getColor(R.color.color_main_background))
    }
    /**
     * **Title: **初始化沉浸式状态栏<br></br>
     * **Description: **子类可重写此方法实现完全自定义状态栏
     */
    protected open fun initImmersionBar() {
        /**
         * 沉浸式状态栏 - 解决布局与状态栏重叠问题vs设置状态栏颜色(支持侧滑返回-状态栏跟随联动，一体化效果,最优先选择)
         */
        ImmersionBar.with(this)
            .fitsSystemWindows(true, setStatusBarColor()) //状态栏颜色
            .statusBarDarkFont(darkFont()) //状态栏字体颜色
            .keyboardEnable(true) //解决软键盘遮挡EditText问题
            .navigationBarColor(setNavigationBarColor())//底部返回键导航栏颜色
            .init()
    }
    /**
     * 子类重写可自定义导航栏颜色
     */
    protected open fun setNavigationBarColor() : Int =  R.color.color_navigation
    /**
     * **Title: **设置状态栏颜色<br></br>
     * **Description: **子类可重写此方法，返回状态栏的颜色引用值
     */
    protected open fun setStatusBarColor(): Int = R.color.color_statusbar

    /**
     * **Title: **颜色状态栏字体颜色<br></br>
     * **Description: **子类可重写此方法,true:状态栏字体黑色 false:状态栏字体白色
     */
    protected open fun darkFont(): Boolean = false

    /**
     * **Title: **关闭软键盘<br></br>
     * **Description: **这里写方法的详细描述
     */
    open fun closeSoftware() {
        val localView = this.currentFocus
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (localView != null) {
            imm.hideSoftInputFromWindow(localView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private fun initSwipeBackFinish() {
        mSwipeBackHelper = BGASwipeBackHelper(this, this)
        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true)
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true)
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true)
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow)
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true)
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true)
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f)
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false)
    }
    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    override fun isSupportSwipeBack(): Boolean = false
    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    override fun onSwipeBackLayoutSlide(slideOffset: Float) {}

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    override fun onSwipeBackLayoutCancel() {}

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    override fun onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding) {
            return
        }
        mSwipeBackHelper.backward()
    }

    fun back(view:View){
        finish()
    }

    /**
     * 界面销毁相关数据资源释放
     */
    override fun onDestroy() {
        super.onDestroy()
        mDataBinding.unbind()
        AppManager.removeActivity(this)
    }

    /**
     * activity进来时的切换动画
     */
    open fun enterActivityAnim(startAnim:Int,exitAnim:Int){
        overridePendingTransition(startAnim,exitAnim)
    }
    /**
     * activity销毁时的切换动画
     */
    override fun finish(){
        super.finish()
        closeSoftware()
        exitActivityAnim(R.anim.translate_lto_between,R.anim.translate_between_to_right)
    }
    open fun exitActivityAnim(startAnim:Int,exitAnim:Int){
        overridePendingTransition(startAnim,exitAnim)
    }
    /**
     * 点击返回键调用finish()执行退出界面动画
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode==KeyEvent.KEYCODE_BACK)finish()
        return super.onKeyDown(keyCode, event)
    }
}
