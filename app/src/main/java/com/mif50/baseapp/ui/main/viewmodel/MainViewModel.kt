package com.mif50.baseapp.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.mif50.baseapp.domain.model.Transaction
import com.mif50.baseapp.domain.usecase.GetErrorUseCase
import com.mif50.baseapp.domain.usecase.GetTransactionUseCase
import com.mif50.baseapp.helper.network.NetworkHelper
import com.mif50.baseapp.helper.rx.SchedulerProvider
import com.mif50.baseapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

interface IMainViewModel {

    val success: LiveData<StateSuccess>
    fun loadTransaction()
    fun loadError()
    sealed class StateSuccess{
        data class Content(val transaction: List<Transaction>) : StateSuccess()
    }
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTransactionUseCase: GetTransactionUseCase,
    private val getErrorUseCase: GetErrorUseCase,
    private val stateHandle: SavedStateHandle,
    schedulerProvider: SchedulerProvider,
    networkHelper: NetworkHelper
) : BaseViewModel(schedulerProvider, networkHelper), IMainViewModel {

    private val _success = MutableLiveData<IMainViewModel.StateSuccess>()
    override val success get() = _success

    override fun loadTransaction() {
        showLoading()
        getTransactionUseCase()
            .subscribeOn(schedulerProvider.io())
            .subscribeBy(
                onSuccess = {response ->
                    hideLoading()
                    _success.postValue(IMainViewModel.StateSuccess.Content(response.transaction))
                }, onError = { error ->
                    hideLoading()
                    handleNetworkError(error)
                }
            )
            .addTo(compositeDisposable)
    }

    override fun loadError() {
        showLoading()
        getErrorUseCase()
            .subscribeOn(schedulerProvider.io())
            .subscribeBy(
                onSuccess = { response ->
                    hideLoading()
                    _success.postValue(IMainViewModel.StateSuccess.Content(response.transaction))
                }, onError = { error->
                    hideLoading()
                    handleNetworkError(error)
                }
            )
            .addTo(compositeDisposable)
    }
}