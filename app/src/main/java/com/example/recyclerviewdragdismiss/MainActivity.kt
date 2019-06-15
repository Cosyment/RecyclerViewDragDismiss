package com.example.recyclerviewdragdismiss

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created on 2019/6/15 0015
 * Created by hechao
 * Description
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_single.setOnClickListener { startActivity(Intent(this, SingleCategoryActivity::class.java)) }
        btn_multiple.setOnClickListener { startActivity(Intent(this, MultipleCategoryActivity::class.java)) }
    }
}