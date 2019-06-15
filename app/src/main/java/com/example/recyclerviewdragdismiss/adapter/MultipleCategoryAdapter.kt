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
class MultipleCategoryAdapter() : RecyclerView.Adapter<MultipleCategoryAdapter.MyViewHolder>(), OnItemDragListener {

    private val TAG = MultipleCategoryAdapter::class.java.canonicalName
    private var mBrowserDatas = arrayListOf<String>()
    private var mNearbyDatas = arrayListOf<String>()
    private var itemTouchHelper: ItemTouchHelper? = null
    private var onItemDragCallback: OnItemDragCallback? = null

    constructor(
        browserDatas: ArrayList<String>,
        nearbyDatas: ArrayList<String>,
        itemTouchHelper: ItemTouchHelper?
    ) : this() {
        this.mBrowserDatas = browserDatas
        this.mNearbyDatas = nearbyDatas
        this.itemTouchHelper = itemTouchHelper
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
                LayoutInflater.from(p0.context).inflate(
                    R.layout.item_title,
                    p0,
                    false
                )
            )
            ITEM_TYPE_BROWSER_CONTENT ->
                ItemBrowserViewHolder(
                    LayoutInflater.from(p0.context).inflate(
                        R.layout.item_browser_content,
                        p0,
                        false
                    ), onItemDragCallback
                )
            ITEM_TYPE_NEARBY_TITLE -> ItemTitleNearbyViewHolder(
                LayoutInflater.from(p0.context).inflate(
                    R.layout.item_title,
                    p0,
                    false
                )
            )
            else -> ItemNearByViewHolder(
                LayoutInflater.from(p0.context).inflate(
                    R.layout.item_nearby_content,
                    p0,
                    false
                ), onItemDragCallback
            )
        }
    }

    override fun onBindViewHolder(p0: MyViewHolder, position: Int) {
        when (p0) {
            is ItemTitleBrowserViewHolder -> p0.textView.text = "最近使用"
            is ItemBrowserViewHolder -> p0.textView.text = mBrowserDatas[position - 1]
            is ItemTitleNearbyViewHolder -> p0.textView.text = "我的小程序"
            else -> p0.textView.text = mNearbyDatas[position - mBrowserDatas.size - 2]
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            ITEM_TYPE_BROWSER_TITLE
        else if (position > 0 && position < mBrowserDatas.size + 1) ITEM_TYPE_BROWSER_CONTENT
        else if (position == mBrowserDatas.size + 1) ITEM_TYPE_NEARBY_TITLE
        else ITEM_TYPE_NEARBY_CONTENT
    }

    override fun getItemCount(): Int = mBrowserDatas.size + mNearbyDatas.size + 2

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        val fromViewType = getItemViewType(fromPosition)
        val toViewType = getItemViewType(toPosition)
        if (fromViewType == ITEM_TYPE_BROWSER_CONTENT) {
            var preItem: String? = null

            //最近分类移动
            if (toViewType == fromViewType) {
                preItem = mBrowserDatas.removeAt(fromPosition - 1)
                mBrowserDatas.add(toPosition - 1, preItem)
                notifyItemMoved(fromPosition, toPosition)
            }

            //最近使用移动到其他分类
            else if (toViewType == ITEM_TYPE_NEARBY_CONTENT) {
                Log.e(TAG, "fromPosition $fromPosition preItem $preItem size ${mBrowserDatas.size}")
                preItem?.let {
                    mNearbyDatas.add(toPosition - (mBrowserDatas.size + 2), it)
                    notifyItemMoved(mBrowserDatas.size, toPosition)
                }
            }

        } else if (fromViewType == ITEM_TYPE_NEARBY_CONTENT) {
            val preItem = mNearbyDatas.removeAt(fromPosition - (mBrowserDatas.size + 2))

            //其他分类移动
            if (toViewType == fromViewType) {
                mNearbyDatas.add(toPosition - (mBrowserDatas.size + 2), preItem)
                notifyItemMoved(fromPosition, toPosition)
            }

            //其他分类移动到最近使用
            else if (toViewType == ITEM_TYPE_BROWSER_CONTENT) {
                mBrowserDatas.add(toPosition - 1, preItem)
                notifyItemMoved(fromPosition, toPosition)
            }
        }
    }

    override fun onItemMoveDistance(viewHolder: RecyclerView.ViewHolder, freed: Boolean, dX: Float, dY: Float) {
        onItemDragCallback?.onItemMoveDistance(viewHolder, freed, dX, dY)
    }

    open class ItemTitleBrowserViewHolder(view: View) :
        MyViewHolder(view, null)

    open class ItemTitleNearbyViewHolder(view: View) :
        MyViewHolder(view, null)

    open class ItemBrowserViewHolder(view: View, onItemDragCallback: OnItemDragCallback?) :
        MyViewHolder(view, onItemDragCallback)

    open class ItemNearByViewHolder(view: View, onItemDragCallback: OnItemDragCallback?) :
        MyViewHolder(view, onItemDragCallback)

    open class DingViewHolder(view: View, onItemDragCallback: OnItemDragCallback?) :
        MyViewHolder(view, onItemDragCallback)

    open class MyViewHolder(view: View, onItemDragCallback: OnItemDragCallback?) : RecyclerView.ViewHolder(view),
        OnItemSelectedListener {
        open val textView: TextView = view.findViewById(R.id.textName) as TextView
        private val onItemDragCallback = onItemDragCallback

        override fun onItemSelected() {
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