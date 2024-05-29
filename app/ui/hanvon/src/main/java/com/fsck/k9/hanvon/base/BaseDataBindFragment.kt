package com.hvmail.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.fsck.k9.hanvon.log.logd
import com.fsck.k9.ui.base.ext.saveAs
import com.fsck.k9.ui.base.ext.saveAsUnChecked

import java.lang.reflect.ParameterizedType

open class BaseDataBindFragment<DB : ViewBinding> : BaseFragment() {
    private var _Binding: DB? = null
    val mBinding get() = _Binding!!


    final override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        logd("$tag    onCreateView")
        val type = javaClass.genericSuperclass
        val vbClass: Class<DB> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        _Binding = method.invoke(this, layoutInflater)!!.saveAsUnChecked()
        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _Binding = null
    }

}
