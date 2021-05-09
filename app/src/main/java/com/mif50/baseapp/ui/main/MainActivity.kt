package com.mif50.baseapp.ui.main

import android.view.LayoutInflater
import com.mif50.baseapp.databinding.ActivityMainBinding
import com.mif50.baseapp.helper.Logger
import com.mif50.baseapp.ui.base.BaseActivity
import com.mif50.baseapp.ui.base.StateError
import com.mif50.baseapp.ui.base.StateLoading
import com.mif50.baseapp.ui.main.viewmodel.IMainViewModel
import com.mif50.baseapp.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun setup() {}

    override fun initObserver() {
        super.initObserver()
        viewModel.success.observe(this, ::handleSuccess)
    }

    override fun setListener() = with(binding) {
        btnLoad.setOnClickListener {
            viewModel.loadTransaction()
        }
        btnLoadError.setOnClickListener { viewModel.loadError() }
    }

    override fun handleLoading(state: StateLoading){
        when(state) {
            is StateLoading.ShowLoading -> { Logger.d(TAG, "handleLoading: showLoading")}
            is StateLoading.HideLoading -> { Logger.d(TAG, "handleLoading: hideLoading") }
        }
    }

    private fun handleSuccess(state: IMainViewModel.StateSuccess) {
        when (state) {
            is IMainViewModel.StateSuccess.Content -> {
                Logger.d(TAG, "renderViewState: ${state.transaction}")
            }
        }
    }

    override fun handleError(state: StateError){
        when(state){
            is StateError.ErrorMessage -> { Logger.d(TAG, "handleError: ${state.message}") }
            is StateError.ErrorResId -> { Logger.d(TAG, "handleError: ${getString(state.resId)}") }
        }
    }
}