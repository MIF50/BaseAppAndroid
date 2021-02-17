package com.mif50.baseapp.helper.ktx

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.mif50.baseapp.R

// Show alert dialog
fun Context.showAlertDialog(positiveButtonLabel : String = getString(R.string.okay),
                            title : String = getString(R.string.app_name), message : String,
                            actionOnPositiveButton : () -> Unit) {
    val builder = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setCancelable(false)
        .setPositiveButton(positiveButtonLabel) { dialog, _ ->
            dialog.cancel()
            actionOnPositiveButton()
        }
    val alert = builder.create()
    alert.show()
}

// Toast extensions
fun Context.showShotToast(message : String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message : String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

// SnackBar Extensions
fun View.showShotSnackBar(message : String){
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun View.showLongSnackBar(message : String){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun View.snackBarWithAction(message : String, actionLabel : String,
                            block : () -> Unit){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .setAction(actionLabel) {
            block()
        }
}