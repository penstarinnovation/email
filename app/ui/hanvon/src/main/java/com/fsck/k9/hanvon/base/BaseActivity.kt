package com.hvmail.common.base

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fsck.k9.hanvon.log.logd

open class BaseActivity : AppCompatActivity() {
    var TAG = javaClass.simpleName + taskId
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd(" $TAG,  onCreate: savedInstanceState = $savedInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        logd(" $TAG,  onRestoreInstanceState:  savedInstanceState = $savedInstanceState")
    }

    override fun onStart() {
        super.onStart()
        logd(" $TAG,  onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, TAG, Toast.LENGTH_SHORT).show()
        logd(" $TAG,  onResume: ")
    }

    override fun onPause() {
        super.onPause()
        logd(" $TAG,  onPause: ")
    }

    override fun onStop() {
        super.onStop()
        logd(" $TAG,  onStop: ")
    }

    override fun onRestart() {
        super.onRestart()
        logd(" $TAG,  onRestart: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        logd(" $TAG,  onDestroy: ")
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        logd(" $TAG,  onNewIntent: ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        logd(" $TAG,  onSaveInstanceState: ")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        logd(" $TAG,  onConfigurationChanged: ")
    }
}
