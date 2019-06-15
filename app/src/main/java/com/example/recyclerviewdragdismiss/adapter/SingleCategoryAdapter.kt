package com.example.recyclerviewdragdismiss.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.recyclerviewdragdismiss.R
import com.example.recyclerviewdragdismiss.listener.OnItemDragListener
import com.example.recyclerviewdragdismiss.listener.OnItemSelectedListener

/**
 * @PageageName : com.example.recyclerviewdragdismiss
 * @Author : hechao
 * @Date :   2019-06-13 19:39
 */
open class SingleCategoryAdapter() :
    RecyclerView.Adapter<SingleCategoryAdapter.MyViewHolder>(),
    OnItemDragListener {

    private var mBrowserDatas = arrayListOf<String>()

    private var itemTouchHelper: ItemTouchHelper? = null
    private var onItemDragCallback: OnItemDragCallback? = null

    constructor(
        browserDatas: ArrayList<String>,
        itemTouchHelper: ItemTouchHelper
    ) : this() {
        this.mBrowserDatas = browserDatas
        this.itemTouchHelper = itemTouchHelper
    }

    companion object {
        const val ITEM_TYPE_BROWSER_TITLE = 0
        const val ITEM_TYPE_BROWSER_CONTENT = 1
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): MyViewHolder {
        return when (viewType) {
            ITEM_TYPE_BROWSER_TITLE -> ItemTitleBrowserViewHolder(
                LayoutInflater.from(p0.context).inflate(
                    R.layout.item_title,
                    p0,
                    false
                )
            )
            else ->
                ItemBrowserViewHolder(
                    LayoutInflater.from(p0.context).inflate(
                        R.layout.item_browser_content,
                        p0,
                        false
                    ),
                    onItemDragCallback
                )
        }
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        when (viewHolder) {
            is ItemTitleBrowserViewHolder -> {
                viewHolder.textView.text = "最近浏览"
            }
            else -> {
                viewHolder.textView.text = mBrowserDatas[position - 1]
                viewHolder.itemView.setOnLongClickListener {
                    itemTouchHelper?.startDrag(viewHolder)
                    false
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            ITEM_TYPE_BROWSER_TITLE
        else ITEM_TYPE_BROWSER_CONTENT
    }

    override fun getItemCount(): Int = mBrowserDatas.size + 1

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        val preItem = mBrowserDatas.removeAt(fromPosition - 1)
        mBrowserDatas.add(
            toPosition - 1,
            preItem
        )
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemMoveDistance(viewHolder: RecyclerView.ViewHolder, freed: Boolean, dX: Float, dY: Float) {
        onItemDragCallback?.onItemMoveDistance(viewHolder, freed, dX, dY)
    }

    open class ItemTitleBrowserViewHolder(view: View) :
        MyViewHolder(view, null)

    open class ItemBrowserViewHolder(view: View, onItemDragCallback: OnItemDragCallback?) :
        MyViewHolder(view, onItemDragCallback)


    open class MyViewHolder(view: View, onItemDragCallback: OnItemDragCallback?) : RecyclerView.ViewHolder(view),
        OnItemSelectedListener {

        open val textView: TextView = view.findViewById(R.id.textName) as TextView
        private var onItemDragCallback = onItemDragCallback

        override fun onItemSelected() {
            Log.e("TAG", "onItemSelected")
            onItemDragCallback?.onItemSelected(this, true)
            itemView.setBackgroundColor(Color.LTGRAY)
            val animatorX = ObjectAnimator.ofFloat(itemView, "scaleX", 1f, 1.1f)
            val animatorY = ObjectAnimator.ofFloat(itemView, "scaleY", 1f, 1.1f)
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(animatorX, animatorY)
            animatorSet.duration = 100
            animatorSet.start()
        }

        override fun onItemClear() {
            Log.e("TAG", "onItemClear")
            onItemDragCallback?.onItemSelected(this, false)
            itemView.setBackgroundColor(0)
            val animatorX = ObjectAnimator.ofFloat(itemView, "scaleX", 1.1f, 1f)
            val animatorY = ObjectAnimator.ofFloat(itemView, "scaleY", 1.1f, 1f)
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(animatorX, animatorY)
            animatorSet.duration = 300
            animatorSet.start()
        }
    }

    fun setOnItemDragCallback(onItemDragCallback: OnItemDragCallback) {
        this.onItemDragCallback = onItemDragCallback
    }

    interface OnItemDragCallback {
        fun onItemSelected(viewHolder: RecyclerView.ViewHolder, selected: Boolean)
        fun onItemMoveDistance(viewHolder: RecyclerView.ViewHolder, freed: Boolean, dX: Float, dY: Float)
    }
}