package com.mif50.baseapp.helper.ktx

import androidx.core.content.ContextCompat
import com.mif50.baseapp.App

fun Int.asColor() = ContextCompat.getColor(App.instance, this)

fun Int.asDrawable() = ContextCompat.getDrawable(App.instance, this)