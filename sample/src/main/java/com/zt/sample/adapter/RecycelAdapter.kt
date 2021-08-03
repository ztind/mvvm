package com.zt.sample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zt.sample.R
import com.zt.sample.beans.MeiZi
import com.zt.sample.databinding.ItemMeiziBinding
import com.zt.sample.utils.ToastUtils

/**
Describe：recycleview列表适配器
Author:ZT
Date:2021/8/3
 */
class RecycelAdapter(val datas:MutableList<MeiZi>):RecyclerView.Adapter<RecycelAdapter.ItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_meizi, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(datas[holder.layoutPosition])
    }

    class ItemViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        private val binding by lazy {
            ItemMeiziBinding.bind(itemView)
        }
        fun bind(data :MeiZi){
            binding.data = data
            itemView.setOnClickListener {
                ToastUtils.showShort(data.author+"-"+data.title)
            }
        }
    }
    fun addDatas(newDatas:MutableList<MeiZi>){
        this.datas.addAll(newDatas)
        notifyDataSetChanged()
    }
    fun refreshDatas(newDatas:MutableList<MeiZi>){
        this.datas.clear()
        this.datas.addAll(newDatas)
        notifyDataSetChanged()
    }
}