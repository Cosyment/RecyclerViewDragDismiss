package com.example.recyclerviewdragdismiss

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

/**
 * @PageageName : com.example.recyclerviewdragdismiss
 * @Author : hechao
 * @Date :   2019-06-13 19:39
 */
class MyAdapter() : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var mDatas = arrayListOf<String>()

    constructor(datas: ArrayList<String>) : this() {
        this.mDatas = datas
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item, p0, false))
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.textView.text = mDatas[p1]
    }

    override fun getItemCount(): Int = mDatas.size

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        val preItem = mDatas.removeAt(fromPosition)
        mDatas.add(if (toPosition in 1 until fromPosition) toPosition - 1 else toPosition, preItem)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun onItemDismiss(position: Int) {
        mDatas.removeAt(position)
        notifyItemRemoved(position)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textName)
    }
}