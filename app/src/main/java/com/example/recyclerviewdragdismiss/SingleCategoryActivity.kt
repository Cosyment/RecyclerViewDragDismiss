package com.example.recyclerviewdragdismiss

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.view.WindowManager
import com.example.recyclerviewdragdismiss.adapter.SingleCategoryAdapter
import com.example.recyclerviewdragdismiss.listener.DragItemHelperCallback
import kotlinx.android.synthetic.main.activity_single_category.*

/**
 * @PageageName : com.example.recyclerviewdragdismiss
 * @Author : hechao
 * @Date :   2019-06-14 22:33
 */
class SingleCategoryActivity : AppCompatActivity() {
    private val TAG = SingleCategoryActivity::class.java.canonicalName
    private val mDatas = arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    private var height = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_category)

        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val screenHeight = windowManager.defaultDisplay.height
        val screenWidth = windowManager.defaultDisplay.width

        val itemTouchHelper = ItemTouchHelper(DragItemHelperCallback())
        itemTouchHelper.attachToRecyclerView(recycler_view)
        recycler_view.adapter =
            SingleCategoryAdapter(mDatas, itemTouchHelper)

        val manager = GridLayoutManager(this, 4)
        recycler_view.layoutManager = manager

        manager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = recycler_view.adapter?.getItemViewType(position)
                    return if (viewType == SingleCategoryAdapter.ITEM_TYPE_BROWSER_TITLE) 4 else 1
                }
            }

        (recycler_view.adapter as SingleCategoryAdapter).setOnItemDragCallback(object :
            SingleCategoryAdapter.OnItemDragCallback {
            override fun onItemMoveDistance(viewHolder: RecyclerView.ViewHolder, freed: Boolean, dX: Float, dY: Float) {
                if (dY >= ll_delete_container.top && freed) {
                    viewHolder.itemView.visibility = View.INVISIBLE
                    if (viewHolder.adapterPosition > 0 && viewHolder.adapterPosition < mDatas.size) {
                        mDatas.removeAt(viewHolder.adapterPosition)
                        recycler_view.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                    }
                }
            }

            override fun onItemSelected(viewHolder: RecyclerView.ViewHolder, selected: Boolean) {
                if (selected && ll_delete_container.visibility == View.INVISIBLE) {
                    ll_delete_container.visibility = View.VISIBLE
                    val animator =
                        ObjectAnimator.ofFloat(ll_delete_container, "translationY", screenHeight.toFloat(), 0f)
                    animator.duration = 300
                    animator.start()
                } else {
                    val animator =
                        ObjectAnimator.ofFloat(ll_delete_container, "translationY", 0f, screenHeight.toFloat())
                    animator.duration = 300
                    animator.addListener(object : Animator.AnimatorListener {
                        override fun onAnimationEnd(p0: Animator?) {
                            ll_delete_container.visibility = View.INVISIBLE
                        }

                        override fun onAnimationCancel(p0: Animator?) {
                        }

                        override fun onAnimationStart(p0: Animator?) {
                        }

                        override fun onAnimationRepeat(p0: Animator?) {
                        }
                    })
                    animator.start()
                }
            }
        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        height = ll_delete_container.height
    }
}