package com.hvmail.legacy.view

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import com.fsck.k9.hanvon.log.logi

class ContactBackRoundEditText : androidx.appcompat.widget.AppCompatEditText {
    private var tag = this::class.java.simpleName
    var callBack: EnterKeyCallBack? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr,
    ) {
        init()
    }

    private fun init() {
        if (!TextUtils.isEmpty(text)) {
            background = resources.getDrawable(
                com.fsck.k9.ui.base.R.drawable.shape_white_stroke_1_black_corners_3, null,
            )
        } else {
            background = null
        }
        setOnEditorActionListener { v, actionId, event ->
            logi("OnEditorActionListener actionId = $actionId  event = $event")
            if ((actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE)) {
                //点击搜索要做的操作
                clearFocus()
                isFocusable = false
                isFocusableInTouchMode = false
                callBack?.onEnterKeyCallBack(text.toString())
                true
            } else {
                false
            }
        }

        setOnLongClickListener {
            logi("OnLongClickListener")
            // 处理长按事件
            isFocusable = true
            isFocusableInTouchMode = true
            true
        }

        onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            logi("hasfocus = $hasFocus")
            if (hasFocus) {
                // EditText 获取焦点时，设置背景颜色
                background = null
            } else {
                // EditText 失去焦点时，设置背景颜色
                if (!TextUtils.isEmpty(text)) {
                    background = resources.getDrawable(
                        com.fsck.k9.ui.base.R.drawable.shape_white_stroke_1_black_corners_3, null,
                    )
                }
            }
        }

        addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    if (hint.isNullOrEmpty()) hint = "请输入联系人"
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if (!hint.isNullOrEmpty()) hint = ""
                }
            },
        )
    }

    interface EnterKeyCallBack {
        fun onEnterKeyCallBack(text: String)
    }
}
