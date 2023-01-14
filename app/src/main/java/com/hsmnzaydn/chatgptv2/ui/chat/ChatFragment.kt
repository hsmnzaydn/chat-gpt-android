package com.hsmnzaydn.chatgptv2.ui.chat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hsmnzaydn.chatgptv2.R
import com.hsmnzaydn.chatgptv2.base.BaseFragment
import com.hsmnzaydn.chatgptv2.databinding.FragmentChatBinding
import com.hsmnzaydn.chatgptv2.ui.adapter.ChatListAdapter
import com.hsmnzaydn.chatgptv2.ui.chat.action.ChatFragmentAction
import com.hsmnzaydn.chatgptv2.ui.chat.state.ChatScreenState
import com.hsmnzaydn.domain.chatgpt.model.UIMessage
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding, ChatViewModel>() {

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

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val value = it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0).toString()
                binding.edtMessage.setText(value)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        subscribeMessages()
        sendMessageAreaManagement()
        enableMicrophone()
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
                    }
                    ChatScreenState.OpenSpeechToTextState -> {
                        openSpeechToText()
                    }
                }
            }
        }
    }


    private fun sendMessageAreaManagement() {
        binding.edtMessage.addTextChangedListener {
            if (it != null && it.isEmpty()) {
                enableMicrophone()
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
        adapter.setData(list)
    }

    private fun enableSendMessage() {
        binding.ivSend.setImageResource(R.drawable.ic_send)
        binding.materialCardView.setOnClickListener {
            viewModel.action(ChatFragmentAction.SendMessageAction(binding.edtMessage.text.toString()))
            binding.edtMessage.text?.clear()
        }
    }

    private fun enableMicrophone() {
        binding.ivSend.setImageResource(R.drawable.ic_mic)
        binding.materialCardView.setOnClickListener {
            viewModel.action(ChatFragmentAction.OpenSpeechToTextAction)
        }
    }

    private fun openSpeechToText(){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            Locale.getDefault()
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.i_am_listen_please_speak))
        getResult.launch(intent)
    }
}