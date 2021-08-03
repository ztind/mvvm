package com.zt.sample.activity

import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.zt.mvvm.viewmodel.BaseViewModel
import com.zt.sample.adapter.RecycelAdapter
import com.zt.sample.beans.MeiZi
import com.zt.sample.common.CommonExceptionHandler
import com.zt.sample.common.CommonTransformHandler
import com.zt.sample.databinding.ActivityNetworkDemoBinding
import com.zt.sample.utils.ToastUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
Describe：文件描述
Author:ZT
Date:2021/8/3
 */
class NetworDemoViewModel :BaseViewModel<NetworDemoRepository,ActivityNetworkDemoBinding>() {
    private val mActivity by lazy {
        mLifecycleOwner as NetworDemokActivity
    }
    private var page = 1
    private val size = 1

    override fun initView() {
        mBinding.smartrefreshlayout.setOnRefreshLoadMoreListener(object :
            OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                page++
                getData()
            }
            override fun onRefresh(refreshLayout: RefreshLayout) {
                page=1
                getData()
            }
        })
    }

    override fun initData() {
        getData()
    }

    private fun getData(){
        viewModelScope.launch {
            mRepository.getData(page,size)
                .onStart {

                }
                .transform {
                    emit(CommonTransformHandler(it))
                }
                .catch {
                    CommonExceptionHandler.handler(it,loadState,mActivity){
                        ToastUtils.showShort(it.message)
                    }
                }
                .onCompletion {
                    finshRefreshAndLoadMor()
                }
                .collectLatest {
                    refreshUI(it)
                }
        }
    }
    fun refreshUI(list:MutableList<MeiZi>?){
        if(list != null && list.size >0){
            initRecycleViewData(list)
        }else{
            if(mBinding.smartrefreshlayout.isLoading){
                mBinding.smartrefreshlayout.finishLoadMoreWithNoMoreData()
            }else if(mBinding.smartrefreshlayout.isRefreshing){
                mBinding.smartrefreshlayout.finishRefresh()
            }
        }
    }
    private var adapter:RecycelAdapter?= null
    fun initRecycleViewData(datas:MutableList<MeiZi>){
        if(adapter==null){
            adapter = RecycelAdapter(datas)
            //线性布局管理器
            mBinding.recycleview.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL,false)
            //set adapter
            mBinding.recycleview.adapter = adapter
        }else{
            when {
                mBinding.smartrefreshlayout.isLoading -> {
                    //上拉加载
                    adapter?.addDatas(datas)
                }
                mBinding.smartrefreshlayout.isRefreshing -> {
                    //下拉刷新
                    adapter?.refreshDatas(datas
                    )
                }
            }
        }
    }
    private fun finshRefreshAndLoadMor(){
        if(mBinding.smartrefreshlayout.isRefreshing)mBinding.smartrefreshlayout.finishRefresh()
        if(mBinding.smartrefreshlayout.isLoading)mBinding.smartrefreshlayout.finishLoadMore()
    }
}