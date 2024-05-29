package com.fsck.k9.hanvon.util

import android.app.Activity
import android.view.ViewGroup
import android.widget.Toast


fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun ViewGroup.toast(msg: String) {
    Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
}

