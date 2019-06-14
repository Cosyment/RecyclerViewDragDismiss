package com.example.recyclerviewdragdismiss

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivitys : AppCompatActivity() {

    private val TAG = MainActivitys::class.java.canonicalName
    private val mDatas = arrayListOf("1", "2", "3", "4", "5")
    //    private val mDatas = arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
    private var height = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val screenHeight = windowManager.defaultDisplay.height
        val screenWidth = windowManager.defaultDisplay.width

        val adapter = MyAdapter(mDatas, mDatas, object : MyAdapter.DragMoveCallback {
            override fun onItemSelected(position: Int) {
                if (ll_delete_container.visibility == View.INVISIBLE)
                    ll_delete_container.visibility = View.VISIBLE
                val animator =
                    ObjectAnimator.ofFloat(ll_delete_container, "translationY", screenHeight.toFloat(), 0f)
                animator.duration = 300
                animator.start()
            }

            override fun onItemClear(position: Int, dX: Float, dY: Float) {
                if (ll_delete_container.visibility == View.VISIBLE) {
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

            override fun onItemDragDistance(
                viewHolder: RecyclerView.ViewHolder,
                fingerUp: Boolean,
                dX: Float,
                dY: Float
            ) {
                if (fingerUp && viewHolder.itemView.visibility == View.VISIBLE) {
                    if (dY >= ll_delete_container.top) {
                        viewHolder.itemView.visibility = View.INVISIBLE
                        mDatas.removeAt(viewHolder.adapterPosition)
                        recycler_view.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                    }
                }
            }
        })
        val itemTouchHelper = ItemTouchHelper(DragItemHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recycler_view)

        val manager = object : GridLayoutManager(this, 2) {
            override fun getSpanCount(): Int {
                return super.getSpanCount()
            }
        }

        manager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = adapter.getItemViewType(position)
                    Log.e("TAG", "viewType $viewType")
                    return if (viewType == 0) 1 else 2
//                    return if (viewType == MyAdapter.ITEM_TYPE_BROWSER_TITLE || viewType == MyAdapter.ITEM_TYPE_NEARBY_TITLE) 1 else 4
                }
            }
        recycler_view.layoutManager = manager
        recycler_view.adapter = adapter
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        height = ll_delete_container.height
    }
}
