package com.hvmail.common.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.fsck.k9.hanvon.util.ActivityCollector
import com.fsck.k9.ui.base.ext.saveAs
import com.fsck.k9.ui.base.ext.saveAsUnChecked
import java.lang.reflect.ParameterizedType

open class BaseDataBindingActivity<DB : ViewBinding> : BaseActivity() {
    private var _Binding: DB? = null
    val mBinding get() = _Binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
        val type = javaClass.genericSuperclass
        val vbClass: Class<DB> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        _Binding = method.invoke(this, layoutInflater)!!.saveAsUnChecked()
        setContentView(mBinding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _Binding = null
        ActivityCollector.removeActivity(this)
    }
}
