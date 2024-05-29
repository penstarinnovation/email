package com.hvmail.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.fsck.k9.hanvon.databinding.DialogEmailBaseBinding
import com.fsck.k9.hanvon.log.logd

const val DIALOG_TAG = "EmailDialog"

abstract class BaseDialogFragment(
    private var vb: ViewBinding,
    private val onDismiss: () -> Unit,
    private var customDialog: Dialog? = null

) : AppCompatDialogFragment() {
    //    val vb get() = _vb!!
    private val mDefaultTag: String by lazy { this.tag ?: javaClass.simpleName }

    private var _binding: DialogEmailBaseBinding? = null
    val binding get() = _binding!!

    //    private val mDialogEmailBinding get() = _binding!!
    abstract val isHaveHead: Boolean
    private var _title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        logd("onCreate")
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            dismissAllowingStateLoss()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        logd("onCreateView")
        _binding = DialogEmailBaseBinding.inflate(layoutInflater)
        binding.clDialogHead.root.visibility = if (isHaveHead) View.VISIBLE else View.GONE
        binding.clDialogHead.tvDialogTitle.text = _title
        binding.vDialogClose.setOnClickListener {
            dismiss()
        }

        binding.llContent.addView(vb.root, 1)
        return binding.root
    }

    override fun dismiss() {
        onDismiss.invoke()
        super.dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        logd("onCreateDialog")
        return if (customDialog != null) {
            customDialog!!
        } else {
            // 不使用 Dialog，替换成 BaseDialog 对象
            AppCompatDialog(requireContext(), theme).apply {
                setCanceledOnTouchOutside(false);
                window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                window?.setSoftInputMode(SOFT_INPUT_ADJUST_RESIZE)
            }.also { customDialog = it }
        }
    }

    private fun removePreFragment(manager: FragmentManager) {
        val transaction = manager.beginTransaction()
        transaction.remove(this)
        transaction.commitAllowingStateLoss()
    }


    fun show(fragment: Fragment) = fragment.activity?.supportFragmentManager?.apply {
        removePreFragment(this)
        super.show(this, DIALOG_TAG)
    }

    fun show(activity: FragmentActivity) = activity.supportFragmentManager.apply {
        removePreFragment(this)
        super.show(this, DIALOG_TAG)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        logd("onDismiss")
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        logd("onCancel")
    }


    override fun onDestroyView() {
        logd("onDestroyView")
        super.onDestroyView()
        binding.llContent.removeView(vb.root)
        _binding = null
    }

    override fun onDestroy() {
        logd("onDestroy")
        super.onDestroy()
    }


    fun setTitle(title: String) {
        _title = title
    }

}


