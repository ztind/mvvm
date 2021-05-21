package com.zt.mvvm.view

import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.zt.mvvm.common.utils.State
import com.zt.mvvm.common.utils.StateType
import com.zt.mvvm.repository.BaseRepository
import com.zt.mvvm.viewmodel.BaseViewModel

abstract class  BaseLifeCycleActivity<VM : BaseViewModel<*,DB>, DB : ViewDataBinding>: BaseActivity<VM, DB>() {

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