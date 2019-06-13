package com.example.recyclerviewdragdismiss

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * @PageageName : com.example.recyclerviewdragdismiss
 * @Author : hechao
 * @Date :   2019-06-13 19:16
 */
class DragItemHelperCallback() : ItemTouchHelper.Callback() {

    private var myAdapter: MyAdapter? = null

    constructor(myAdapter: MyAdapter?) : this() {
        this.myAdapter = myAdapter
    }

    override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val moveFlags = 0
        return makeMovementFlags(dragFlags, moveFlags)
    }

    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
        myAdapter?.let {
            it.onItemMove(p1.adapterPosition, p2.adapterPosition)
        }
        return true
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
        myAdapter?.let {
            it.onItemDismiss(p1)
        }
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }
}