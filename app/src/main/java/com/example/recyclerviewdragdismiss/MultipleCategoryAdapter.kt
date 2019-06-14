package com.example.recyclerviewdragdismiss

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * @PageageName : com.example.recyclerviewdragdismiss
 * @Author : hechao
 * @Date :   2019-06-13 19:39
 */
class MultipleCategoryAdapter() : RecyclerView.Adapter<MultipleCategoryAdapter.MyViewHolder>() {

    private var mBrowserDatas = arrayListOf<String>()

    private var dragMoveCallback: DragMoveCallback? = null

    constructor(
        browserDatas: ArrayList<String>,
        dragMoveCallback: DragMoveCallback?
    ) : this() {
        this.mBrowserDatas = browserDatas
        this.dragMoveCallback = dragMoveCallback
    }

    companion object {
        const val ITEM_TYPE_BROWSER_TITLE = 0
        const val ITEM_TYPE_BROWSER_CONTENT = 1
        const val ITEM_TYPE_NEARBY_TITLE = 2
        const val ITEM_TYPE_NEARBY_CONTENT = 3
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): MyViewHolder {
        return when (viewType) {
            ITEM_TYPE_BROWSER_TITLE -> ItemTitleBrowserViewHolder(
                LayoutInflater.from(p0.context).inflate(R.layout.item_title, p0, false)
            )
            else ->
                ItemBrowserViewHolder(
                    LayoutInflater.from(p0.context).inflate(R.layout.item_browser_content, p0, false), dragMoveCallback
                )
        }
    }

    override fun onBindViewHolder(p0: MyViewHolder, position: Int) {
        when (p0) {
            is ItemTitleBrowserViewHolder -> p0.textView.text = "最近浏览"
            else -> p0.textView.text = mBrowserDatas[position - 1]
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            ITEM_TYPE_BROWSER_TITLE
        else ITEM_TYPE_BROWSER_CONTENT
    }

    override fun getItemCount(): Int = mBrowserDatas.size + 1

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        val preItem = mBrowserDatas.removeAt(fromPosition - 1)
        mBrowserDatas.add(
            toPosition - 1,
            preItem
        )
        notifyItemMoved(fromPosition, toPosition)
    }

    fun onItemDismiss(position: Int) {
        if (position >= 0) {
            mBrowserDatas.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    open class ItemTitleBrowserViewHolder(view: View) :
        MyViewHolder(view, null)

    open class ItemTitleNearbyViewHolder(view: View, dragMoveCallback: DragMoveCallback?) :
        MyViewHolder(view, dragMoveCallback)

    open class ItemBrowserViewHolder(view: View, dragMoveCallback: DragMoveCallback?) :
        MyViewHolder(view, dragMoveCallback)

    open class ItemNearByViewHolder(view: View, dragMoveCallback: DragMoveCallback?) :
        MyViewHolder(view, dragMoveCallback)

    open class DingViewHolder(view: View, dragMoveCallback: DragMoveCallback?) : MyViewHolder(view, dragMoveCallback)

    open class MyViewHolder(view: View, dragMoveCallback: DragMoveCallback?) : RecyclerView.ViewHolder(view) {
        open val textView: TextView = view.findViewById(R.id.textName) as TextView
        private val dragMoveCallback = dragMoveCallback
        private var mDX = 0f
        private var mDY = 0f

        fun onItemSelected(position: Int) {
            dragMoveCallback?.let { it.onItemSelected(position) }
            itemView.setBackgroundColor(Color.LTGRAY)
            val animatorX = ObjectAnimator.ofFloat(itemView, "scaleX", 1f, 1.1f)
            val animatorY = ObjectAnimator.ofFloat(itemView, "scaleY", 1f, 1.1f)
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(animatorX, animatorY)
            animatorSet.duration = 100
            animatorSet.start()
        }

        fun onItemDragDistance(viewHolder: RecyclerView.ViewHolder, fingerUp: Boolean, dX: Float, dY: Float) {
            mDX = dX
            mDY = dY
            dragMoveCallback?.let { it.onItemDragDistance(viewHolder, fingerUp, dX, dY) }
        }

        fun onItemClear(position: Int) {
            dragMoveCallback?.let { it.onItemClear(position, mDX, mDY) }
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
        fun onItemSelected(position: Int)
        fun onItemDragDistance(viewHolder: RecyclerView.ViewHolder, fingerUp: Boolean, dX: Float, dY: Float)
        fun onItemClear(position: Int, dX: Float, dY: Float)
    }
}