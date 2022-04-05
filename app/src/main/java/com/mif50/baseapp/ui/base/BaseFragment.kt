package com.mif50.baseapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding,VM: ViewModel> : Fragment() {

    // TODO: ViewBinding
    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    protected val binding: VB get() = _binding as VB

    // TODO: ViewModel
    protected val viewModel: VM by lazy { ViewModelProvider(this)[getViewModelClass()] }
    protected abstract fun getViewModelClass(): Class<VM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        initObserver()
    }
    open fun initObserver(){}

    abstract fun setup()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}