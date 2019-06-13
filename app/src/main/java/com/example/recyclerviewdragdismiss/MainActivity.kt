package com.example.recyclerviewdragdismiss

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.helper.ItemTouchHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mDatas = arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.adapter = MyAdapter(mDatas)
        val itemTouchHelper = ItemTouchHelper(DragItemHelperCallback(recycler_view.adapter as MyAdapter))
        itemTouchHelper.attachToRecyclerView(recycler_view)
    }
}
