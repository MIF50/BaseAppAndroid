package com.mif50.baseapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding,VM : BaseViewModel> : AppCompatActivity() {

    // TODO: ViewBinding
    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    // TODO: ViewModel
    protected val viewModel: VM by lazy { ViewModelProvider(this)[getViewModelClass()] }
    protected abstract fun getViewModelClass(): Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        setup()
        initObserver()
        setListener()
    }

    abstract fun setup()

    open fun initObserver() {
        viewModel.loading.observe(this,::handleLoading)
        viewModel.error.observe(this,::handleError)
    }

    open fun setListener() {}

    open fun handleLoading(state: StateLoading){}

    open fun handleError(state: StateError){}

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
        _binding = null
    }
}