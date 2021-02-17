package com.mif50.baseapp.helper.ktx

import androidx.fragment.app.Fragment

// Fragment related
inline fun <reified T: Any> Fragment.getValue(label: String, default: T? = null) = lazy {
    val value = arguments?.get(label)
    if (value is T) value else default
}

inline fun <reified T: Any> Fragment.getValueNonNull(label: String, default: T? = null) = lazy {
    val value = arguments?.get(label)
    requireNotNull(if (value is T) value else default) { label }
}