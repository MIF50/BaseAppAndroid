package com.mif50.baseapp.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mif50.baseapp.R
import com.mif50.baseapp.data.local.pref.PrefStorage
import com.mif50.baseapp.helper.Logger
import com.mif50.baseapp.helper.network.NetworkHelper
import com.mif50.baseapp.helper.network.StateNetworkException.CONNECTION_EXCEPTION
import com.mif50.baseapp.helper.network.StateNetworkException.DEFAULT_EXCEPTION
import com.mif50.baseapp.helper.network.StateNetworkException.NETWORK_EXCEPTION
import com.mif50.baseapp.helper.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

sealed class StateError {
    data class ErrorMessage(val message: String): StateError()
    data class ErrorResId(val resId: Int): StateError()
}

sealed class StateLoading {
    data class ShowLoading(val loading: Boolean = true): StateLoading()
    data class HideLoading(val loading: Boolean = false): StateLoading()
}

abstract class BaseViewModel(
    protected val schedulerProvider: SchedulerProvider,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    @Inject lateinit var prefStorage: PrefStorage

    companion object {
        private const val TAG = "BaseViewModel"
    }

    protected val compositeDisposable = CompositeDisposable()

     val error: MutableLiveData<StateError> = MutableLiveData()
     val loading: MutableLiveData<StateLoading> = MutableLiveData()

    protected fun showLoading(){
        loading.postValue(StateLoading.ShowLoading())
    }

    protected fun hideLoading() {
        loading.postValue(StateLoading.HideLoading())
    }

    protected fun checkInternetWithMessage(): Boolean =
        if (networkHelper.isNetworkConnected()) {
            true
        } else {
            error.postValue(StateError.ErrorResId(R.string.network_connection_error))
            false
        }

    protected fun checkInternet(): Boolean = networkHelper.isNetworkConnected()

    protected fun handleNetworkError(err: Throwable?) {
        err?.let {
            networkHelper.castToNetworkError(it).run {
                when (code) {
                    DEFAULT_EXCEPTION -> {
                        Logger.d(TAG, "code = 0 , error message = $message")
                        error.postValue(StateError.ErrorMessage(message))
                    }
                    CONNECTION_EXCEPTION -> {
                        Logger.d(TAG, "code = -1 , error message = network_default_error")
                        error.postValue(StateError.ErrorResId(R.string.network_default_error))
                    }
                    NETWORK_EXCEPTION -> {
                        Logger.d(TAG, "code = -2 , error message = network_connection_error")
                        error.postValue(StateError.ErrorResId(R.string.network_connection_error))
                    }
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> {
                        forcedLogoutUser()
                        error.postValue(StateError.ErrorResId(R.string.server_connection_error))
                    }
                    HttpsURLConnection.HTTP_INTERNAL_ERROR -> {
                        error.postValue(StateError.ErrorResId(R.string.network_internal_error))

                    }
                    HttpsURLConnection.HTTP_UNAVAILABLE -> {
                        error.postValue(StateError.ErrorResId(R.string.network_server_not_available))

                    }
                    else -> {
                        error.postValue(StateError.ErrorMessage(message))
                    }
                }
            }
        }
    }

    protected open fun forcedLogoutUser() {
        // do something
    }

    open fun clear(){
        onCleared()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}