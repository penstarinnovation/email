package com.fsck.k9.mail.store.imap

interface IdleRefreshManager {
    fun canScheduleTimers(): Boolean
    fun startTimer(timeout: Long, callback: () -> Unit): IdleRefreshTimer
    fun resetTimers()
}

interface IdleRefreshTimer {
    val isWaiting: Boolean
    fun cancel()
}
