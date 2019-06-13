package com.example.recyclerviewdragdismiss

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mDatas = arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    private var height = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val screenHeight = windowManager.defaultDisplay.height
        val screenWidth = windowManager.defaultDisplay.width
        Log.e("tag","screenHeight $screenHeight")
        recycler_view.adapter = MyAdapter(mDatas, object : MyAdapter.DragMoveCallback {
            override fun onItemSelected() {
                if (ll_delete_container.visibility == View.INVISIBLE)
                    ll_delete_container.visibility = View.VISIBLE
                val animator =
                    ObjectAnimator.ofFloat(ll_delete_container, "translationY", screenHeight.toFloat(), 0f)
                animator.duration = 300
                animator.start()
            }

            override fun onItemClear() {
                if (ll_delete_container.visibility == View.VISIBLE) {
                    val animator =
                        ObjectAnimator.ofFloat(ll_delete_container, "translationY", 0f, screenHeight.toFloat())
                    animator.duration = 300
                    animator.addListener(object :Animator.AnimatorListener{
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
        val itemTouchHelper = ItemTouchHelper(DragItemHelperCallback(recycler_view.adapter as MyAdapter))
        itemTouchHelper.attachToRecyclerView(recycler_view)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        height = ll_delete_container.height
    }
}
