package com.fsck.k9.hanvon.util

import android.app.Activity

object ActivityCollector {
    private var activities: MutableList<Activity> = ArrayList()
    fun addActivity(activity: Activity) = activities.add(activity)


    fun removeActivity(activity: Activity) = activities.remove(activity)

    fun removeAll() {
        for (activity in activities) {
            if (!activity.isFinishing)
                activity.finish()
        }
        activities.clear()
    }

    fun kill() = android.os.Process.killProcess(android.os.Process.myPid())
}

