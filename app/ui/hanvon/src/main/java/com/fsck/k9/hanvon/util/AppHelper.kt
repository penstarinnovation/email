package com.fsck.k9.hanvon.util

import android.app.Application

object AppHelper {
    private lateinit var app: Application

    fun init(application: Application) {
        this.app = application
    }

    /**
     * 获取全局应用
     */
    fun getApplication() = app
}
