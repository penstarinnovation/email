package com.fsck.k9.hanvon.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class OnVerticalScrollListener(var callBack: onScrolledCallBack? = null) :
    RecyclerView.OnScrollListener() {
    var lastVisibleItemPosition = -1
    var firstVisibleItemPosition = -1
    var firstCompletelyVisibleItemPosition = -1
    var lastCompletelyVisibleItemPosition = -1

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        callBack?.onScrollStateChanged(newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        val layoutManager = recyclerView.layoutManager
        if (layoutManager is LinearLayoutManager) {
            lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            firstCompletelyVisibleItemPosition =
                layoutManager.findFirstCompletelyVisibleItemPosition()
            lastCompletelyVisibleItemPosition =
                layoutManager.findLastCompletelyVisibleItemPosition()

            if (firstCompletelyVisibleItemPosition == 0) {
                //滑动到顶部
                callBack?.onScrolledToTop() //滑到顶部
            }
            if (lastCompletelyVisibleItemPosition == layoutManager.getItemCount() - 1) {
                //滑动到底部
                callBack?.onScrolledToBottom() //滑到底部
            }

        } else if (layoutManager is StaggeredGridLayoutManager) {
            val positions = IntArray(layoutManager.spanCount)
            layoutManager.findLastVisibleItemPositions(positions)
            layoutManager.findFirstVisibleItemPositions(positions)
            layoutManager.findFirstCompletelyVisibleItemPositions(positions)
            layoutManager.findLastCompletelyVisibleItemPositions(positions)

            var maxPosition = positions[0]
            var minPosition = positions[0]
            for (position in positions) {
                maxPosition = Math.max(maxPosition, position)
                minPosition = Math.min(minPosition, position)
            }

        } else {
            if (!recyclerView.canScrollVertically(-1)) {
                callBack?.onScrolledToBottom() //滑到底部
            } else if (!recyclerView.canScrollVertically(1)) {
                callBack?.onScrolledToTop() //滑到顶部
            }
        }


        if (dy > 0) {
            callBack?.onScrolledUp()
        } else if (dy < 0) {
            callBack?.onScrolledDown()
        }
        callBack?.onScrolled(firstVisibleItemPosition, lastVisibleItemPosition)
    }

}

interface onScrolledCallBack {
    fun onScrollStateChanged(newState: Int)
    fun onScrolled(first: Int, last: Int)
    fun onScrolledUp()

    fun onScrolledDown()

    fun onScrolledToTop()

    fun onScrolledToBottom()
}
