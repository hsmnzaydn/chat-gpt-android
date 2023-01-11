package com.hsmnzaydn.chatgptv2.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hsmnzaydn.chatgptv2.R
import com.hsmnzaydn.chatgptv2.databinding.DialogLoadingBinding

class LoadingFragmentDialog : DialogFragment() {
    lateinit var binding: DialogLoadingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogLoadingBinding
            .inflate(LayoutInflater.from(context))
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    companion object {
        fun newInstance() = LoadingFragmentDialog()
    }
}