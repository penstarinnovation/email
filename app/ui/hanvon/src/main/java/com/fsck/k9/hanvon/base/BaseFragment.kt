package com.hvmail.common.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fsck.k9.hanvon.log.logd

open class BaseFragment : Fragment() {
    internal val tag = this::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd("$tag     onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logd("$tag    onViewCreated")
    }

    override fun onResume() {
        super.onResume()
        logd("$tag    onResume")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        logd("$tag    onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        logd("$tag    onDestroy")
    }
}
