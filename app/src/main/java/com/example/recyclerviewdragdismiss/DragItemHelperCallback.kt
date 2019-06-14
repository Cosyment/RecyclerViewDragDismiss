package com.example.recyclerviewdragdismiss

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View

/**
 * @PageageName : com.example.recyclerviewdragdismiss
 * @Author : hechao
 * @Date :   2019-06-13 19:16
 */
class DragItemHelperCallback() : ItemTouchHelper.Callback() {

    private val TAG = DragItemHelperCallback::class.java.canonicalName
    private var myAdapter: MyAdapter? = null
    private var mAdapterSelectedPosition = -1
    private var mFingerUp = false


    constructor(myAdapter: MyAdapter?) : this() {
        this.myAdapter = myAdapter
    }

    override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val moveFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, moveFlags)
    }

    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
        myAdapter?.let {
            it.onItemMove(p1.adapterPosition, p2.adapterPosition)
        }
        return true
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        mFingerUp = false
        val myViewHolder = viewHolder as MyAdapter.MyViewHolder
        myViewHolder.onItemClear(viewHolder.adapterPosition)
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, direction: Int) {
        myAdapter?.let {
            it.onItemDismiss(direction)
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            val myViewHolder = viewHolder as MyAdapter.MyViewHolder
            mAdapterSelectedPosition = myViewHolder.adapterPosition
            myViewHolder.onItemSelected(mAdapterSelectedPosition)
        }
    }

    override fun getAnimationDuration(
        recyclerView: RecyclerView,
        animationType: Int,
        animateDx: Float,
        animateDy: Float
    ): Long {
        mFingerUp = true
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            val myViewHolder = viewHolder as MyAdapter.MyViewHolder
            myViewHolder.onItemDragDistance(viewHolder, mFingerUp, dX, dY + myViewHolder.itemView.top)
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}