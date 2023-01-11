package com.hsmnzaydn.chatgptv2.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hsmnzaydn.domain.chatgpt.model.UIMessage

class MessageDiffUtil(
    private val oldList: List<UIMessage>,
    private val newList: List<UIMessage>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].messageId == newList[newItemPosition].messageId
    }
}