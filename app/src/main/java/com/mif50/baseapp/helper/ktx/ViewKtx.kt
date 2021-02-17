package com.mif50.baseapp.helper.ktx

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.remove(){
    this.visibility = View.GONE
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.setViewHeight(value : Int){
    layoutParams.height = value
}