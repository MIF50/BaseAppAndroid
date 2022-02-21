package com.mif50.baseapp.helper.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

inline fun <T> AppCompatActivity.observe(data: LiveData<T>, crossinline block: (T) -> Unit) {
    data.observe(this) {
        block(it)
    }
}

inline fun <reified T: Any> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T: Any> Context.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T: Any> Context.startActivityWithClearTask() {
    startActivity(
        Intent(
            this,
            T::class.java
        ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    )
}

inline fun <reified T: Any> FragmentActivity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T: Any> Activity.startActivity(data: Intent) {
    startActivity(Intent(this, T::class.java).putExtras(data))
}

inline fun <reified T: Any> Context.startActivity(data: Intent) {
    startActivity(Intent(this, T::class.java).putExtras(data))
}

inline fun <reified T: Any> Context.startActivityWithClearTask(data: Intent) {
    startActivity(Intent(this, T::class.java).putExtras(data).addClearFlags())
}

inline fun <reified T: Any> Activity.startActivityForResult(requestCode: Int) {
    startActivityForResult(Intent(this, T::class.java), requestCode)
}

inline fun <reified T: Any> Activity.startActivityForResult(data: Intent, requestCode: Int) {
    startActivityForResult(Intent(this, T::class.java).putExtras(data), requestCode)
}

fun Intent.addClearFlags() = this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)

// Activity related
inline fun <reified  T : Any> Activity.getValue(
    label : String, default : T? = null) = lazy{
    val value = intent?.extras?.get(label)
    if (value is T) value else default
}

inline fun <reified  T : Any> Activity.getValueNonNull(
    label : String, default : T? = null) = lazy{
    val value = intent?.extras?.get(label)
    requireNotNull((if (value is T) value else default)){label}
}