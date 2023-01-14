package com.hsmnzaydn.chatgptv2.ui.chat.action

sealed class ChatFragmentAction{
    data class SendMessageAction(val message: String) : ChatFragmentAction()
    object OpenSpeechToTextAction : ChatFragmentAction()
    object EnableSendMessageAction : ChatFragmentAction()
    object EnableMicrophoneAction : ChatFragmentAction()
}