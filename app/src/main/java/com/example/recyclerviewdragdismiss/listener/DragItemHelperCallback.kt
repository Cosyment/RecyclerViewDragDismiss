package com.example.recyclerviewdragdismiss.listener

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import com.example.recyclerviewdragdismiss.adapter.MultipleCategoryAdapter

/**
 * @PageageName : com.example.recyclerviewdragdismiss
 * @Author : hechao
 * @Date :   2019-06-13 19:16
 */
class DragItemHelperCallback : ItemTouchHelper.Callback() {

    private val TAG = DragItemHelperCallback::class.java.canonicalName
    private var mFreed = false


    override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val moveFlags = 0
        return makeMovementFlags(dragFlags, moveFlags)
    }

    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
        if (p1.itemViewType == MultipleCategoryAdapter.ITEM_TYPE_NEARBY_TITLE || p1.itemViewType == MultipleCategoryAdapter.ITEM_TYPE_BROWSER_TITLE || p2.itemViewType == MultipleCategoryAdapter.ITEM_TYPE_NEARBY_TITLE || p2.itemViewType == MultipleCategoryAdapter.ITEM_TYPE_BROWSER_TITLE) return false
        if (p0.adapter is OnItemDragListener) {
            val onItemDragListener = p0.adapter as OnItemDragListener
            onItemDragListener.onItemMove(p1.adapterPosition, p2.adapterPosition)
        }
        return true
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        mFreed = false
        if (viewHolder is OnItemSelectedListener) {
            val onItemSelectedListener = viewHolder as OnItemSelectedListener
            onItemSelectedListener.onItemClear()
        }
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, direction: Int) {
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
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
        mFreed = true
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
            onItemDragListener.onItemMoveDistance(viewHolder, mFreed, dX, dY + viewHolder.itemView.top)
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}