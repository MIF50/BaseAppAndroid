package com.mif50.baseapp.helper.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.mif50.baseapp.helper.Logger
import com.mif50.baseapp.helper.network.StateNetworkException.CONNECTION_EXCEPTION
import com.mif50.baseapp.helper.network.StateNetworkException.DEFAULT_EXCEPTION
import com.mif50.baseapp.helper.network.StateNetworkException.NETWORK_EXCEPTION
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Singleton

@Singleton
class NetworkHelper constructor(
        private val context: Context
) {

    companion object {
        private const val TAG = "NetworkHelper"
    }

    fun isNetworkConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities =
                        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
                    }
                }
            } else {
                try {
                    val activeNetworkInfo = connectivityManager.activeNetworkInfo
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                        return true
                    }
                } catch (e: Exception) {
                }
            }
        }
        return false
    }

     fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw  = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

     fun castToNetworkError(throwable: Throwable): NetworkError {
        val defaultNetworkError = NetworkError(message = throwable.message ?: "Something wrong happened, please retry again...")
        try {
            if (throwable is ConnectException) {
                Logger.d(TAG, "ConnectException")
                return NetworkError(code = CONNECTION_EXCEPTION)
            }
            if (throwable is UnknownHostException) {
                Logger.d(TAG, "UnknownHostException")
                return NetworkError(code = NETWORK_EXCEPTION)
            }
            if (throwable !is HttpException) {
                Logger.d(TAG, "is not HttpException, ${throwable.message}")
                return NetworkError(code = DEFAULT_EXCEPTION, message = throwable.message ?: "Something wrong happened, please retry again...")
            }
            val error = fromJson(throwable.response()?.errorBody()?.string())
            Logger.d(TAG, "error = ${error.message}")
            return NetworkError(code = DEFAULT_EXCEPTION, response = error.response,message =  error.message)

        } catch (e: IOException) {
            Logger.e(TAG, e.toString())
        } catch (e: JsonDataException) {
            Logger.e(TAG, e.toString())
        } catch (e: NullPointerException) {
            Logger.e(TAG, e.toString())
        }
        return defaultNetworkError
    }

    private fun fromJson(json: String?): NetworkError {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(NetworkError::class.java)
        return jsonAdapter.fromJson(json ?: "{}") ?: NetworkError()
    }
}