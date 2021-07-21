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

abstract class BaseLifeCycleFragment<VM : BaseViewModel<BaseRepository,VB>,VB : ViewDataBinding> : BaseFragment<VM, VB>(){

    override fun initView() {
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
                }
            }
        }
    }
    open fun showLoading() {}
    open fun showSuccess() {}
    open fun showError(msg: String) {
        if (!TextUtils.isEmpty(msg)) Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }
    open fun showEmpty() {}
    abstract fun initDataObserver()
}