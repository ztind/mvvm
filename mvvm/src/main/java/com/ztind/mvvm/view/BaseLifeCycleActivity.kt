package com.ztind.mvvm.view

import androidx.databinding.ViewDataBinding
import com.ztind.mvvm.viewmodel.BaseViewModel

abstract class  BaseLifeCycleActivity<VM : BaseViewModel<*,VB>, VB : ViewDataBinding>: BaseActivity<VM, VB>() {

    override fun initView() {
        super.initView()
//        //全局网络请求状态监听
//        mViewModel.loadState.observe(this,observer)
//        //子类livedata数据观察监听init
//        initDataObserver()
    }
    /**
     * 分发应用状态
     */
//    val observer by lazy {
//        Observer<State>{
//            it?.let {
//                when (it.code) {
//                    StateType.LOADING -> showLoading()
//                    StateType.SUCCESS -> showSuccess()
//                    StateType.ERROR -> showError(it.message)
//                    StateType.EMPTY -> showEmpty()
//                    else -> {
//                        showError("未知错误")
//                    }
//                }
//            }
//        }
//    }
//    open fun showLoading() {}
//    open fun showSuccess() {}
//    open fun showError(msg: String) { if (!TextUtils.isEmpty(msg))Toast.makeText(this, msg, Toast.LENGTH_SHORT).show() }
//    open fun showEmpty() {}
//    protected open fun initDataObserver(){}
}