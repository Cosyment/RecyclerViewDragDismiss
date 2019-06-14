package com.example.recyclerviewdragdismiss

import android.support.v7.widget.RecyclerView

/**
 * @PageageName : com.example.recyclerviewdragdismiss
 * @Author : hechao
 * @Date :   2019-06-14 23:43
 */
interface OnItemDragListener {

    /**
     * view拖动
     */
    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemMoveDistance(viewHolder: RecyclerView.ViewHolder,dX: Float, dY: Float)
}