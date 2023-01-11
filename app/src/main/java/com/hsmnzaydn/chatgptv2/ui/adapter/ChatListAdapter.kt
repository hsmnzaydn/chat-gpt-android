package com.hsmnzaydn.chatgptv2.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.hsmnzaydn.chatgptv2.R
import com.hsmnzaydn.domain.chatgpt.model.MessageSender
import com.hsmnzaydn.domain.chatgpt.model.UIMessage

class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.DataAdapterViewHolder>() {
    private var messages: List<UIMessage> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {

        val layout = when (viewType) {
            MessageSender.ME.ordinal -> R.layout.row_outcoming_message
            MessageSender.OPPOSITE.ordinal -> R.layout.row_incoming_message
            else -> throw IllegalArgumentException("Invalid view type")
        }

        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)

        return DataAdapterViewHolder(view)
    }

    fun setData(messages: List<UIMessage>) {
        val diffResult = DiffUtil.calculateDiff(MessageDiffUtil(this.messages, messages))
        diffResult.dispatchUpdatesTo(this)
        this.messages = messages
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class DataAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private fun bindOutComingMessage(item: UIMessage) {
            (itemView.findViewById<View>(R.id.tv_message) as TextView).text = item.message
        }

        private fun bindInComingMessage(item: UIMessage) {
            val textView = (itemView.findViewById<View>(R.id.tv_message) as TextView)
            textView.text = item.message

        }

        fun bind(uiMessage: UIMessage) {
            if (uiMessage.messageSender === MessageSender.OPPOSITE) {
                bindInComingMessage(uiMessage)
            } else {
                bindOutComingMessage(uiMessage)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message: UIMessage = messages.get(position)
        return message.messageSender.ordinal
    }

    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size
}