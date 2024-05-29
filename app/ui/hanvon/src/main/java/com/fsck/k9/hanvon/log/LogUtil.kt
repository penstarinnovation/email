package com.fsck.k9.hanvon.log

import com.fsck.k9.logging.Timber

fun logi(obj: Any?) {
    Timber.logger.i(obj.toString())
}

fun logi(message: String, obj: Any?) {
    Timber.logger.i(message, obj)
}

fun logd(obj: Any?) {
    Timber.logger.d(obj.toString())
}

fun logd(message: String, obj: Any?) {
    Timber.logger.d(message, obj)
}

fun loge(throwable: Throwable?, message: String = "", vararg anys: Any) {
    Timber.logger.e(throwable, message, anys)
}

fun loge(message: String, vararg anys: Any) {
    Timber.logger.e(message, anys)
}
