package com.fsck.k9.hanvon.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsck.k9.controller.MessageReference
import com.fsck.k9.ui.messagelist.MessageListConfig
import com.fsck.k9.ui.messagelist.MessageListInfo
import com.fsck.k9.ui.messagelist.MessageListLiveData
import com.fsck.k9.ui.messagelist.MessageListLiveDataFactory
import com.fsck.k9.ui.messagelist.MessageSortOverride
import java.util.LinkedList

class EmailHomeViewModel(private val messageListLiveDataFactory: MessageListLiveDataFactory) : ViewModel() {

    //是否处于操作状态
    public val actionMode : MutableLiveData<Boolean> = MutableLiveData(false)

    private var currentMessageListLiveData: MessageListLiveData? = null
    private val messageListLiveData = MediatorLiveData<MessageListInfo>()

    val messageSortOverrides = LinkedList<Pair<MessageReference, MessageSortOverride>>()

    fun loadMessageList(config: MessageListConfig) {
        if (currentMessageListLiveData?.config == config) return
        //加载数据
        val liveData = messageListLiveDataFactory.create(viewModelScope, config)
        currentMessageListLiveData = liveData

        messageListLiveData.addSource(liveData) { items ->
            messageListLiveData.value = items
        }
    }


}
