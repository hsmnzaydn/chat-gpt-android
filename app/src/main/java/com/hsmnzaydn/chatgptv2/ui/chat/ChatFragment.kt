package com.hsmnzaydn.chatgptv2.ui.chat

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hsmnzaydn.chatgptv2.base.BaseFragment
import com.hsmnzaydn.chatgptv2.databinding.FragmentChatBinding
import com.hsmnzaydn.chatgptv2.ui.adapter.ChatListAdapter
import com.hsmnzaydn.chatgptv2.ui.chat.action.ChatFragmentAction
import com.hsmnzaydn.chatgptv2.ui.chat.state.ChatScreenState
import com.hsmnzaydn.chatgptv2.utils.AdmobUtility
import com.hsmnzaydn.domain.chatgpt.model.UIMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding, ChatViewModel>() {

    private val admob by lazy {
        AdmobUtility(requireActivity())
    }

    override val viewModel: ChatViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean,
    ): FragmentChatBinding {
        return FragmentChatBinding.inflate(inflater, container, attachToParent)
    }

    private val adapter by lazy {
        ChatListAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        admob.init()
        initAdapter()
        subscribeMessages()
        sendMessageAreaManagement()

        binding.materialCardView.setOnClickListener {
            viewModel.action(ChatFragmentAction.SendMessageAction(binding.edtMessage.text.toString()))
            binding.edtMessage.text?.clear()
        }
    }


    private fun subscribeMessages() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it) {
                    is ChatScreenState.LoadMessagesState -> {
                        setAdapterData(it.messages)
                    }
                    ChatScreenState.LoadingState -> {

                    }
                    is ChatScreenState.WaitingMessageState -> {
                        setAdapterData(it.messages)
                    }
                    is ChatScreenState.ShowAdState -> {
                        setAdapterData(it.messages)
                        admob.show()
                    }
                }
            }
        }
    }


    private fun sendMessageAreaManagement() {
        binding.edtMessage.addTextChangedListener {
            if (it != null && it.isEmpty()) {
                disableSendMessage()
            } else {
                enableSendMessage()
            }
        }
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.stackFromEnd = true;

        binding.rv.layoutManager = layoutManager
        binding.rv.adapter = adapter
    }

    private fun setAdapterData(list: List<UIMessage>) {
        disableSendMessage()
        adapter.setData(list)
    }

    private fun enableSendMessage() {
        binding.materialCardView.backgroundTintList =
            ColorStateList.valueOf(requireActivity().getResources().getColor(android.R.color.holo_green_dark))
        binding.materialCardView.isClickable = true
        binding.materialCardView.isEnabled = true
    }

    private fun disableSendMessage() {
        binding.materialCardView.backgroundTintList =
            ColorStateList.valueOf(requireActivity().getResources().getColor(android.R.color.darker_gray))
        binding.materialCardView.isClickable = false
        binding.materialCardView.isEnabled = false
    }

}