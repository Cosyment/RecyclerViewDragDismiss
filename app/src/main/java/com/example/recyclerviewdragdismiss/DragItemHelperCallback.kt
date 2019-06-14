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

    private val TAG = DragItemHelperCallback::class.java.canonicalName
    private var myAdapter: SingleCategoryAdapter? = null
    private var mAdapterSelectedPosition = -1
    private var mFingerUp = false
    private var mSelected = false;

    constructor(myAdapter: SingleCategoryAdapter?) : this() {
        this.myAdapter = myAdapter
    }

    override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val moveFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, moveFlags)
    }

    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
        if (p1.itemViewType != p2.itemViewType) return false
        if (p0.adapter is OnItemDragListener) {
            val onItemDragListener = p0.adapter as OnItemDragListener
            onItemDragListener.onItemMove(p1.adapterPosition, p2.adapterPosition)
        }
        return true
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        mFingerUp = false
        if (viewHolder is OnItemSelectedListener) {
            val onItemSelectedListener = viewHolder as OnItemSelectedListener
            onItemSelectedListener.onItemClear()
        }
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, direction: Int) {
//        myAdapter?.let {
//            it.onItemDismiss(direction)
//        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            mSelected = true
            if (viewHolder is OnItemSelectedListener) {
                val onItemSelectedListener = viewHolder as OnItemSelectedListener
                onItemSelectedListener.onItemSelected()
            }
        }
    }

    override fun getAnimationDuration(
        recyclerView: RecyclerView,
        animationType: Int,
        animateDx: Float,
        animateDy: Float
    ): Long {
        mFingerUp = true
        mSelected = false
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
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && recyclerView.adapter is OnItemDragListener) {
            val onItemDragListener = recyclerView.adapter as OnItemDragListener
            onItemDragListener.onItemMoveDistance(viewHolder,dX, dY + viewHolder.itemView.top)
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}