package com.mif50.baseapp.data.network

import com.mif50.baseapp.data.network.response.DummySuccessResponse
import com.mif50.baseapp.helper.SimpleResult
import io.reactivex.Single
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ApiService {

    // https://api.mocki.io/v1/4e08cbf3

    @POST(Endpoints.DUMMY_SUCCESS)
    fun dummySuccessApi(): Single<DummySuccessResponse>

    @POST(Endpoints.DUMMY_ERROR)
    fun dummyErrorApi(): Single<DummySuccessResponse>
}