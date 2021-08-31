package com.ztind.mvvm.view
import android.os.Bundle
import android.view.View

/**
 * 懒加载fragment:即当前fragment显示时(fragment对用户可见时)才去加载数据
 */
abstract class BaseLazyFragment : BaseFragmentB() {

    /**
     * Fragment 中的 View 是否创建完毕
     */
    protected var isViewCreated: Boolean = false
    /**
     * Fragment 是否对用户可见
     */
    protected var isVisibled: Boolean = false
    /**
     * Fragment 左右切换时，只在第一次显示时请求数据
     */
    protected var isFirstLoad: Boolean = true


    /**
     * Fragment 中view 创建完成的回调
     * onCreateView方法之后调用
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!isViewCreated) {
            initView(view,savedInstanceState)
            isViewCreated = true
        }
        lazyLoad()
    }

    /**
     * setUserVisibleHint：重写关键方法，当前fragment是否对用户可见
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            isVisibled = true
            onVisible()
        } else {
            isVisibled = false
            onInvisible()
        }
    }

    private fun onInvisible() {}

    private fun onVisible() {
        lazyLoad()
    }

    /**
     * (1) isViewCreated 参数在系统调用 onViewCreated 时设置为 true,这时onCreateView方法已调用完毕(一般我们在这方法
     * 里执行findviewbyid等方法),确保 loadData()方法不会报空指针异常。
     * <p>
     * (2) isVisible 参数在 fragment 可见时通过系统回调 setUserVisibileHint 方法设置为true,不可见时为false，
     * 这是 fragment 实现懒加载的关键。
     * <p>
     * (3) isFirstLoad 确保 ViewPager 来回切换时 TabFragment 的 loadData()方法不会被重复调用，loadData()在该
     * Fragment 的整个生命周期只调用一次,第一次调用 loadData()方法后马上执行 isFirst = false。
     */
    private fun lazyLoad() {
        if (isViewCreated && isVisibled && isFirstLoad) {
            loadData()
            isFirstLoad = false
        }
    }

    /**
     * 子类实现加载数据
     */
    protected abstract fun loadData()
    /**
     * 子类实现布局控件初始化
     */
    protected abstract fun initView(rootView: View,savedInstanceState: Bundle?)
}