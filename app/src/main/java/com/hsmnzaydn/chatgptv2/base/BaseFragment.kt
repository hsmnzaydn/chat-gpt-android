package com.hsmnzaydn.chatgptv2.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.hsmnzaydn.chatgptv2.ui.dialog.LoadingFragmentDialog

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    private val loadingFragmentDialog = LoadingFragmentDialog.newInstance()

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean = false
    ): VB

    open fun setListeners() {}

    open suspend fun setReceivers() {}

    open fun isSaveViewState(): Boolean {
        return isViewCreated
    }

    private var isViewCreated = false

    abstract val viewModel: VM

    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (::binding.isInitialized) {
            return binding.root
        }

        binding = getViewBinding(inflater, container)
        viewModel.loadingState.observe(viewLifecycleOwner) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        return binding.root
    }

    private fun showLoading() {
        if (loadingFragmentDialog.isAdded.not()){
            loadingFragmentDialog.show(
                childFragmentManager, LoadingFragmentDialog.Companion::class.java.simpleName
            )
        }
    }

    private fun hideLoading() {
        if (loadingFragmentDialog.isAdded){
            loadingFragmentDialog.dismiss()
        }
    }
}