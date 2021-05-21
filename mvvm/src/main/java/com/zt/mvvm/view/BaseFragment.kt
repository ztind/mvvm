package com.zt.mvvm.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zt.mvvm.common.log.LoggerUtil
import com.zt.mvvm.common.utils.CommonUtil
import com.zt.mvvm.repository.BaseRepository
import com.zt.mvvm.viewmodel.BaseViewModel

/**
 * Fragment基类
 */
abstract class BaseFragment<VM : BaseViewModel<*,DB>,DB : ViewDataBinding>: Fragment(){
    //lateinit 则用于只能生命周期流程中进行获取或者初始化的变量，比如 Android 的 onCreate()
    lateinit var mContext : Context
    protected var rootView : View? = null
    protected lateinit var mDataBinding :DB
    protected lateinit var mViewModel :VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(rootView==null){
            mDataBinding = DataBindingUtil.inflate(inflater,setLayoutResId(),container,false)
            mDataBinding.lifecycleOwner = this
            mViewModel = ViewModelProvider(this).get(CommonUtil.getClass(this))
            mViewModel.mBinding = mDataBinding
            rootView = mDataBinding.root
            initView()
            mViewModel.initView()
            mViewModel.initData()
        }
        return rootView
    }
    abstract fun setLayoutResId():Int

    protected open fun initView(){}

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mContext = activity
    }
}