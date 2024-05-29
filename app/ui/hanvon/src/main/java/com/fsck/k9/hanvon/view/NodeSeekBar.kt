package com.hvmail.legacy.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.fsck.k9.hanvon.R
import com.fsck.k9.hanvon.util.dp2px
import kotlin.properties.Delegates

/**
 *
 */
class NodeSeekBar : View {
    companion object {
        const val TAG = "NodeSeekBar"
    }

    private val nodeList = mutableListOf<Node>()
    private lateinit var _textArray: Array<String>//attrs
    private var _completeNum by Delegates.notNull<Int>()//attrs
    private var _fillColor by Delegates.notNull<Int>()//attrs
    private var _backgroundColor by Delegates.notNull<Int>()//attrs
    private lateinit var _completeIcon: Bitmap//attrs
    private lateinit var _unCompleteIcon: Bitmap//attrs
    private var _barHeight by Delegates.notNull<Float>()//attrs
    private var _strokeWidth by Delegates.notNull<Float>()//attrs
    private var _radius by Delegates.notNull<Float>()//attrs
    private var _textSize by Delegates.notNull<Float>()//attrs
    private var _textTopMargin by Delegates.notNull<Float>()//attrs

    private var _barCenterX by Delegates.notNull<Float>()
    private var _barCenterY by Delegates.notNull<Float>()

    private var _top by Delegates.notNull<Float>() //上 绘制边界
    private var _left by Delegates.notNull<Float>()//左 绘制边界
    private var _right by Delegates.notNull<Float>()//右 绘制边界
    private var _bottom by Delegates.notNull<Float>()//下 绘制边界

    private val _path = Path()
    private lateinit var _backGroundRectF: RectF
    private lateinit var _fillRectF: RectF
    private val _paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mWidth by Delegates.notNull<Float>()
    private var mHeight by Delegates.notNull<Float>()

    fun changeData(list: List<String>?) {
        nodeList.clear()
        list?.let {
            for (name in list) {
                nodeList.add(Node(name))
            }
        }
        postInvalidate()
    }

    fun changCompleteIndex(num: Int) {
        _completeNum = when {
            num < 0 -> 0
            num > nodeList.size -> nodeList.size
            else -> num
        }
        postInvalidate()
    }

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr,
    ) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
//        val typedArray = context.obtainStyledAttributes(
//            R.styleable.NodeSeekBar
//        )
//
//        val typedArray = context.obtainStyledAttributes(
//            R.style.Widget_Theme_App_Kotlin_MyNodeSeekbar, R.styleable.NodeSeekBar
//        )
//
//        val typedArray = context.obtainStyledAttributes(
//            attrs, R.styleable.NodeSeekBar
//        )

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.NodeSeekBar,
            R.attr.SeekBarStyle,
            R.style.Widget_Theme_Email_NodeSeekbar,
        )


        _textArray = resources.getStringArray(
            typedArray.getResourceId(
                R.styleable.NodeSeekBar_seek_bar_node_text, R.array.account_setup_node_list,
            ),
        )
        if (_textArray.size > 0) {
            changeData(_textArray.toList())
        }

        _completeNum = typedArray.getInt(R.styleable.NodeSeekBar_seek_bar_complete_num, 0)
        changCompleteIndex(_completeNum)

        _completeIcon = typedArray.getDrawable(
            R.styleable.NodeSeekBar_seek_bar_icon_complete,
        )?.toBitmap() ?: BitmapFactory.decodeResource(
            Resources.getSystem(), R.drawable.icon_node_seek_complete,
        )

        _unCompleteIcon =
            typedArray.getDrawable(R.styleable.NodeSeekBar_seek_bar_icon_un_complete)?.toBitmap()
                ?: BitmapFactory.decodeResource(
                    Resources.getSystem(), R.drawable.icon_node_seek_uncomplete,
                )

        _fillColor = typedArray.getColor(R.styleable.NodeSeekBar_seek_bar_fill_color, Color.BLACK)

        _backgroundColor =
            typedArray.getColor(R.styleable.NodeSeekBar_seek_bar_background_color, Color.WHITE)

        _barHeight = typedArray.getDimension(
            R.styleable.NodeSeekBar_seek_bar_height, R.dimen.seek_bar_height.dp2px,
        )

        _strokeWidth = typedArray.getDimension(
            R.styleable.NodeSeekBar_seek_bar_stroke_width, R.dimen.seek_bar_stroke_width.dp2px,
        )
        _radius = typedArray.getDimension(
            R.styleable.NodeSeekBar_seek_bar_node_radius, R.dimen.seek_bar_node_radius.dp2px,
        )

        _textSize = typedArray.getDimension(
            R.styleable.NodeSeekBar_seek_bar_text_size, R.dimen.seek_bar_text_size.dp2px,
        )

        _textTopMargin = typedArray.getDimension(
            R.styleable.NodeSeekBar_seek_bar_text_top_margin, R.dimen.seek_bar_text_top_margin.dp2px,
        )

        _unCompleteIcon = Bitmap.createScaledBitmap(
            _unCompleteIcon, _radius.toInt() * 2, _radius.toInt() * 2, true,
        )
        _completeIcon = Bitmap.createScaledBitmap(
            _completeIcon, _radius.toInt() * 2, _radius.toInt() * 2, true,
        )
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            getDefaultSize(suggestedMinimumWidth, widthMeasureSpec),
            getDefaultSize(suggestedMinimumHeight, heightMeasureSpec),
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
        mWidth = measuredWidth.toFloat()
        mHeight = measuredHeight.toFloat()
//        logd("mWidth = $mWidth mHeight=$mHeight")

        _top = top.toFloat() + paddingTop
        _left = left.toFloat() + paddingLeft
        _right = right.toFloat() - paddingRight
        _bottom = bottom.toFloat() - paddingBottom


        _barCenterX = left + mWidth / 2
        _barCenterY = if (_barHeight > _radius * 2) {
            _top + _barHeight / 2
        } else {
            _top + _radius
        }

//        logd("_top = $_top _right = $_right _left = $_left  _bottom = $_bottom")
//
//        logd("_barHeight = $_barHeight")
//
//        logd("_radius = $_radius")

        _backGroundRectF = RectF(
            _left + _radius,
            _barCenterY - _barHeight / 2,
            _right - _radius,
            _barCenterY + _barHeight / 2,
        )
//        logd("_backGroundRectF = $_backGroundRectF")
    }

    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)
        if (nodeList.size < 2) {
            return
        }

        //第一步确定长度和高度画bar
        _paint.reset()
        _path.reset()
        _paint.color = _fillColor
        _paint.strokeWidth = _strokeWidth
        _paint.style = Paint.Style.STROKE
        _path.addRect(_backGroundRectF, Path.Direction.CCW)
        canvas.drawPath(_path, _paint)

        //第二部确定node位置
        //头部
        nodeList[0].location = NodeLocation(_backGroundRectF.left, _barCenterY)
        //尾部
        nodeList[nodeList.size - 1].location = NodeLocation(_backGroundRectF.right, _barCenterY)
        //其他
        if (nodeList.size > 2) {
            val denominator = (nodeList.size - 2) + 1
            for (index in 1..nodeList.size - 2) {
                nodeList[index].location = NodeLocation(
                    _backGroundRectF.left + (_backGroundRectF.width() * (index.toDouble() / denominator.toDouble())).toFloat(),
                    _barCenterY,
                )
            }
        }

//        //第三步，确定首个未完成的点
//        var flag = true
//        for (node in nodeList) {
//            flag = flag and node.isComplete
//        }
//        var unCompleteNodeIndex: Int? = 0
//        if (flag) {
//            unCompleteNodeIndex = nodeList.size - 1
//        } else {
//            for (index in 0 until nodeList.size) {
//                if (!nodeList[index].isComplete) {
//                    unCompleteNodeIndex = index
//                    break
//                }
//            }
//        }

        //第四步填充bar条
        _paint.reset()
        _path.reset()
        when {
            _completeNum <= 0 -> {
            }

            _completeNum >= nodeList.size -> {
                _fillRectF = RectF(
                    _backGroundRectF.left,
                    _backGroundRectF.top,
                    nodeList[nodeList.size - 1].location!!.x,
                    _backGroundRectF.bottom,
                )
                _path.addRect(_fillRectF, Path.Direction.CW)
            }

            else -> {
                _fillRectF = RectF(
                    _backGroundRectF.left,
                    _backGroundRectF.top,
                    nodeList[_completeNum].location!!.x,
                    _backGroundRectF.bottom,
                )
                _path.addRect(_fillRectF, Path.Direction.CW)
            }
        }

        _paint.color = _fillColor
        _paint.style = Paint.Style.FILL
        canvas.drawPath(_path, _paint)

        //第五步画node的icon
        _paint.reset()
        _path.reset()
        nodeList.forEachIndexed { index, node ->
            node.location?.let {
                canvas.drawBitmap(
                    if (index <= _completeNum - 1) _completeIcon else _unCompleteIcon,
                    it.x - _radius,
                    _barCenterY - _radius,
                    _paint,
                )
            }
        }
//
//        //第六步画node的文字
        _paint.reset()
        _paint.textSize = _textSize
        _paint.textAlign = Paint.Align.CENTER
        nodeList.forEachIndexed { index, node ->
            if (_barHeight > _radius * 2) {
                node.location?.let {
                    canvas.drawText(
                        node.tag, it.x, it.y + _barHeight / 2 + _textTopMargin, _paint,
                    )
                }
            } else {
                node.location?.let {
                    canvas.drawText(
                        node.tag, it.x, it.y + _radius + _textTopMargin, _paint,
                    )
                }
            }

        }
    }
}

data class Node(
    val tag: String, var location: NodeLocation? = null,
)

data class NodeLocation(val x: Float, val y: Float)
