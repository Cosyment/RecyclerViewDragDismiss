package com.example.recyclerviewdragdismiss

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
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
    private var dragMoveCallback: DragMoveCallback? = null

    constructor(datas: ArrayList<String>, dragMoveCallback: DragMoveCallback?) : this() {
        this.mDatas = datas
        this.dragMoveCallback = dragMoveCallback
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item, p0, false), dragMoveCallback)
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

    open class MyViewHolder(view: View, dragMoveCallback: DragMoveCallback?) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textName)
        private val dragMoveCallback = dragMoveCallback

        fun onItemSelected() {
            dragMoveCallback?.let { it.onItemSelected() }
            itemView.setBackgroundColor(Color.LTGRAY)
            val animatorX = ObjectAnimator.ofFloat(itemView, "scaleX", 1f, 1.1f)
            val animatorY = ObjectAnimator.ofFloat(itemView, "scaleY", 1f, 1.1f)
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(animatorX, animatorY)
            animatorSet.duration = 100
            animatorSet.start()
        }

        fun onItemClear() {
            dragMoveCallback?.let { it.onItemClear() }
            itemView.setBackgroundColor(0)
            val animatorX = ObjectAnimator.ofFloat(itemView, "scaleX", 1.1f, 1f)
            val animatorY = ObjectAnimator.ofFloat(itemView, "scaleY", 1.1f, 1f)
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(animatorX, animatorY)
            animatorSet.duration = 300
            animatorSet.start()
        }
    }

    interface DragMoveCallback {

        fun onItemSelected()
        fun onItemClear()
    }
}