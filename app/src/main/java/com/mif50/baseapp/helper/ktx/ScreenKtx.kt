package com.mif50.baseapp.helper.ktx

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.WindowManager
import androidx.annotation.ColorRes
import java.lang.ref.WeakReference


val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

val Context.screenHeight: Int
    get() = resources.displayMetrics.heightPixels

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Context.setStatusBarColor(context: WeakReference<Activity>, @ColorRes colorResId: Int) {
    if (Build.VERSION.SDK_INT >= 21) {
        val window = context.get()?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.statusBarColor = colorResId.asColor()
    }
}