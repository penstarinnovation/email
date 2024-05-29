package com.fsck.k9.hanvon.util

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class MyLinearLayoutManager(
    context: Context,
    private val canV: Boolean = false,
    private val canH: Boolean = false
) :
    LinearLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
        return canV
    }

    override fun canScrollHorizontally(): Boolean {
        return canH
    }
}
