package com.zt.mvvm.viewmodel

import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.zt.mvvm.common.utils.CommonUtil
import com.zt.mvvm.common.utils.State
import com.zt.mvvm.common.utils.StateType
import com.zt.mvvm.repository.BaseRepository
import com.zt.mvvm.view.BaseActivity

/**
 * ViewModel基类
 * 在其内部初始化Model实例
 * 其子类定义对Coroutine协程和LiveData的赋值操作的具体实现
 *
 * viewmodel里持有：宿主Activity/Fragment，Databinding,Reposition实例三剑客
 */
abstract class BaseViewModel<R : BaseRepository,DB:ViewDataBinding>: ViewModel(){
    /**
     * viewmodel的Repository
     * 通过反射注入R实例（实例class对象调用其构造方法实例化对象）
     */
    val mRepository : R by lazy {
        // 获取对应Repository实例
        (CommonUtil.getClass<R>(this))
            // 获取构造函数的构造器
            .getDeclaredConstructor()
            //创建实例
            .newInstance()
    }
    /**
     * viewmodel宿主Activity/Fragment,子类用时需要强制转化为对于的Activity/Fragment
     */
    val mLifecycleOwner by lazy{
        mBinding.lifecycleOwner
    }
    /**
     * viewmodel宿主Activity/Fragment所绑定的databinding
     */
    lateinit var mBinding : DB

    /**
     * 初始化vm的view和data
     */
    internal fun gotoInit(){
        //全局网络请求状态监听
        loadState.observe(mLifecycleOwner!!,observer)
        initView()
        initData()
    }

    /**
     * 布局相关view数值初始化，监听事件注册，一些工具类成员初始化都可在此方法中进行实现
     */
    abstract fun initView()
    /**
     * 初始化数据，接口请求
     */
    abstract fun initData()

    /**
     * UI加载的全局状态封装
     */
    val loadState by lazy {
        MutableLiveData<State>()
    }
    /**
     * 分发应用状态
     */
    val observer by lazy {
        Observer<State>{
            it?.let {
                when (it.state) {
                    StateType.LOADING -> showLoading()
                    StateType.SUCCESS -> showSuccess()
                    StateType.ERROR -> showError(it.message)
                    StateType.EMPTY -> showEmpty()
                }
            }
        }
    }
    //全局ui操作处理
    open fun showLoading() {

    }
    open fun showSuccess() {

    }
    open fun showError(msg: String) {

    }
    open fun showEmpty() {

    }
}
