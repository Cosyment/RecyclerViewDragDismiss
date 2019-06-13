package com.example.recyclerviewdragdismiss

import android.graphics.Canvas
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
        return makeMovementFlags(dragFlags, 0)
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

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            val myViewHolder = viewHolder as MyAdapter.MyViewHolder
            myViewHolder.onItemSelected()
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val myViewHolder = viewHolder as MyAdapter.MyViewHolder
        myViewHolder.onItemClear()
    }

    override fun isLongPressDragEnabled(): Boolean = true

    override fun isItemViewSwipeEnabled(): Boolean = true

//    override fun onChildDraw(
//        c: Canvas,
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        dX: Float,
//        dY: Float,
//        actionState: Int,
//        isCurrentlyActive: Boolean
//    ) {
//        if (actionState != ItemTouchHelper.ACTION_STATE_SWIPE) {
//            val width = viewHolder.itemView.width
//            val height = viewHolder.itemView.height
//            val alpah = 1.0 - (Math.abs(dX) / width + Math.abs(dY) / height)
//            viewHolder.itemView.alpha = alpah.toFloat()
//            viewHolder.itemView.translationX = dX
//            viewHolder.itemView.translationY = dY
//        } else {
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//        }
//    }
}