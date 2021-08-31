package com.zt.mvvm.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zt.mvvm.common.utils.CommonUtil
import com.zt.mvvm.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * Fragment基类
 */
abstract class BaseFragment<VM : BaseViewModel<*,VB>,VB : ViewDataBinding>: Fragment(){
    //lateinit 则用于只能生命周期流程中进行获取或者初始化的变量，比如 Android 的 onCreate()
    lateinit var mContext : Context
    protected var rootView : View? = null
    protected lateinit var mDataBinding :VB
    protected lateinit var mViewModel :VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(rootView==null){
            //利用反射，调用指定ViewBinding中的inflate方法填充视图
            val superclass = javaClass.genericSuperclass
            val aClass = (superclass as ParameterizedType).actualTypeArguments[1] as Class<*>
            val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java,ViewGroup::class.java, Boolean::class.java)
            mDataBinding = method.invoke(null, layoutInflater, container, false) as VB

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

    protected open fun initView(){}

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mContext = activity
    }
}