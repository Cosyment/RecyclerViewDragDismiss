package com.example.recyclerviewdragdismiss.listener

/**
 * @PageageName : com.example.recyclerviewdragdismiss
 * @Author : hechao
 * @Date :   2019-06-15 00:09
 */
interface OnItemSelectedListener{

    /**
     * 长按执行
     */
    fun onItemSelected()

    /**
     * 释放手指
     */
    fun onItemClear()
}