package com.fsck.k9.hanvon.util

import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import com.fsck.k9.hanvon.constant.Const.random
import java.util.Random

fun generateRandomName(length: Int = 8): String {
    val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray()
    val result = StringBuilder(length)
    for (i in 1..length) {
        result.append(characters[random.nextInt(characters.size - 1)])
    }
    return result.toString()
}

fun generateRandomNum(bound: Int): Int {
    return random.nextInt(bound)
}

fun generateRandomPrice(length: Int = 5): Int {
    val characters = "0123456789".toCharArray()
    val random = Random()
    val result = StringBuilder(length)
    for (i in 1..length) {
        result.append(characters[random.nextInt(characters.size)])
    }
    return result.toString().toInt()
}

val Float.dp2px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics,
    )
val Float.sp2px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics,
    )
val Int.dp2px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics,
    )
val Int.sp2px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics,
    )

val Float.px2dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics,
    )

val Float.px2sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics,
    )

val Int.px2dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics,
    )

val Int.px2sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics,
    )

/**
 * 当前值*分数
 * @param molecule 分子
 * @param denominator 分母
 * @return Int
 */
fun Int.grade(molecule: Int, denominator: Int): Int {
    return (this * molecule) / denominator
}

fun View.getCenterX(): Float = this.x + this.width / 2

fun View.getCenterY(): Float = this.y + this.height / 2

//fun <T> isInit(properties: KMutableProperty0<T>):Boolean{
//    return properties.isInitialized
//}

fun Int.toStringRGBColor(): String {
    val stringBuffer = StringBuffer()
    stringBuffer.append("#")
    stringBuffer.append(Integer.toHexString(Color.red(this)))
    stringBuffer.append(Integer.toHexString(Color.green(this)))
    stringBuffer.append(Integer.toHexString(Color.blue(this)))
    return stringBuffer.toString()
}

private val provinces = mapOf(
    "直辖市" to "市",
    "安徽" to "皖",
    "福建" to "闽",
    "甘肃" to "甘",
    "广东" to "粤",
    "广西" to "桂",
    "贵州" to "黔",
    "海南" to "琼",
    "河北" to "冀",
    "河南" to "豫",
    "黑龙江" to "黑",
    "湖北" to "鄂",
    "湖南" to "湘",
    "江苏" to "苏",
    "江西" to "赣",
    "吉林" to "吉",
    "辽宁" to "辽",
    "内蒙古" to "蒙",
    "宁夏" to "宁",
    "青海" to "青",
    "山东" to "鲁",
    "山西" to "晋",
    "陕西" to "陕",
    "四川" to "川",
    "新疆" to "新",
    "西藏" to "藏",
    "云南" to "云",
    "浙江" to "浙",
    "其他" to "外",
)

fun toAbbreviation(province: String): String? {
    return provinces[province]
}

fun toProvince(abbreviation: String): String? {
    return provinces.entries.find { it.value == abbreviation }?.key
}



