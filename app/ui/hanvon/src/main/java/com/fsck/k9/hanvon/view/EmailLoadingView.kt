package com.hvmail.legacy.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import com.fsck.k9.hanvon.R
import com.fsck.k9.hanvon.util.dp2px

import kotlin.properties.Delegates

class EmailLoadingView : View {
    //    private lateinit var _completeIcon: Bitmap//attrs
//    private lateinit var _unCompleteIcon: Bitmap//attrs
//    private val _completeShader: Shader by lazy {
//        BitmapShader(_completeIcon, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
//    }
//    private val _unCompleteShader: Shader by lazy {
//        BitmapShader(_unCompleteIcon, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
//    }
    private var _timeTick: Long = 2 * 1000
    private var _num by Delegates.notNull<Int>()//attrs
    private var _radius by Delegates.notNull<Float>()//attrs
    private var _width by Delegates.notNull<Float>()//attrs
    private var _height by Delegates.notNull<Float>()//attrs

    private var _now = 0
    private val nodeList = mutableListOf<RectF>()
    private val radii by lazy {
        floatArrayOf(_radius, _radius, _radius, _radius, _radius, _radius, _radius, _radius)
    }
    private val _pathComplete = Path()
    private val _pathUncomplete = Path()
    private val _paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mWidth by Delegates.notNull<Float>()
    private var mHeight by Delegates.notNull<Float>()
    private val _runnable = object : Runnable {
        override fun run() {
            _now = ++_now % _num
            //logd(" _now = $_now  ")
            invalidate()
        }
    }

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr,
    ) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.EmailLoadingView,
            R.attr.EmailLoadingViewStyle,
            R.style.Widget_Theme_Email_LoadingView,
        )
        _num = typedArray.getInt(
            R.styleable.EmailLoadingView_email_loading_view_node_num,
            R.integer.email_loading_view_node_num,
        )

        _radius = typedArray.getDimension(
            R.styleable.EmailLoadingView_email_loading_view_node_radius,
            R.dimen.email_loading_view_node_radius.dp2px,
        )

        _width = typedArray.getDimension(
            R.styleable.EmailLoadingView_email_loading_view_node_width,
            R.dimen.email_loading_view_node_width.dp2px,
        )
        _height = typedArray.getDimension(
            R.styleable.EmailLoadingView_email_loading_view_node_height,
            R.dimen.email_loading_view_node_height.dp2px,
        )

//        val unCompleteIconDrawable =
//            typedArray.getDrawable(R.styleable.EmailLoadingView_emial_loading_view_node_icon_un_complete)
//                ?: resources.getDrawable(R.drawable.shape_black_stroke_1_corners_3, null)
//        _unCompleteIcon = unCompleteIconDrawable.toBitmap(_radius.toInt() * 2, _radius.toInt() * 2)
//
//        val completeIconDrawable =
//            typedArray.getDrawable(R.styleable.EmailLoadingView_emial_loading_view_node_icon_complete)
//                ?: resources.getDrawable(R.drawable.shape_black_corners_3, null)
//        _completeIcon = completeIconDrawable.toBitmap(_radius.toInt() * 2, _radius.toInt() * 2)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            getDefaultSize(suggestedMinimumWidth, widthMeasureSpec),
            getDefaultSize(suggestedMinimumHeight, heightMeasureSpec) + _radius.toInt(),
        )
    }

    /**
     * Called from layout when this view should
     * assign a size and position to each of its children.
     *
     * Derived classes with children should override
     * this method and call layout on each of
     * their children.
     * @param changed This is a new size or position for this view
     * @param left Left position, relative to parent
     * @param top Top position, relative to parent
     * @param right Right position, relative to parent
     * @param bottom Bottom position, relative to parent
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        //logd("top = $top  left = $left   right = $right   bottom = $bottom")

        mWidth = measuredWidth.toFloat()
        mHeight = measuredHeight.toFloat()

        // logd("mWidth = $mWidth  mHeight=$mHeight")

        val interval = (mWidth - _width * _num) / (_num - 1)

        // logd("interval = $interval")

        nodeList.clear()
        if (nodeList.size == 0) {
            for (index in 1.._num) {
                val _left = left + (index - 1) * interval + (index - 1) * _width
                val _right = _left + _width
                val _top = top.toFloat()
                val _bottom = _top + _height
                val rectF = RectF(_left, _top, _right, _bottom)
                //logd("_now = ${index - 1}   recf = $rectF")
                nodeList.add(rectF)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        removeCallbacks(_runnable)
        _pathComplete.reset()
        _pathUncomplete.reset()
        for (index in 0 until nodeList.size) {
            val rectF = nodeList[index]
            if (_now == index) {
//                _paint.shader = _completeShader
                _pathComplete.addRoundRect(rectF, radii, Path.Direction.CW)
            } else {
//                _paint.shader = _unCompleteShader
                _pathUncomplete.addRoundRect(rectF, radii, Path.Direction.CW)
            }
        }

        _paint.reset()
        _paint.style = Paint.Style.FILL_AND_STROKE
        _paint.color = Color.BLACK
        canvas.drawPath(_pathComplete, _paint)


        _paint.reset()
        _paint.style = Paint.Style.STROKE
        _paint.strokeWidth = 1F.dp2px
        _paint.color = Color.BLACK
        canvas.drawPath(_pathUncomplete, _paint)

        postDelayed(_runnable, _timeTick)
    }

    fun resetProgress() {
        _now = 0
        invalidate()
    }
}
