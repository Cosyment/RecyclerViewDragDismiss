package com.example.recyclerviewdragdismiss.listener

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

    fun onItemMoveDistance(viewHolder: RecyclerView.ViewHolder, freed: Boolean, dX: Float, dY: Float)
}