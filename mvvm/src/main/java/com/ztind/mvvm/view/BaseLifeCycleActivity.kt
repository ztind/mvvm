package com.ztind.mvvm.view

import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.ztind.mvvm.common.utils.State
import com.ztind.mvvm.common.utils.StateType
import com.ztind.mvvm.viewmodel.BaseViewModel
import androidx.lifecycle.Observer

abstract class  BaseLifeCycleActivity<VM : BaseViewModel<*,VB>, VB : ViewDataBinding>: BaseActivity<VM, VB>() {

    override fun initView() {
        super.initView()
        //全局网络请求状态监听
        mViewModel.loadState.observe(this,observer)
        //子类livedata数据观察监听init
        initDataObserver()
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
                    else -> {
                        showError("未知错误")
                    }
                }
            }
        }
    }
    open fun showLoading() {}
    open fun showSuccess() {}
    open fun showError(msg: String) { if (!TextUtils.isEmpty(msg)) Toast.makeText(this, msg, Toast.LENGTH_SHORT).show() }
    open fun showEmpty() {}
    protected open fun initDataObserver(){}
}