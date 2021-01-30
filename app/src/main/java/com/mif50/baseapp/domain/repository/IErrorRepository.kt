package com.mif50.baseapp.domain.repository

import com.mif50.baseapp.data.network.ApiService
import com.mif50.baseapp.data.network.response.DummySuccessResponse
import com.mif50.baseapp.helper.SimpleResult
import com.mif50.baseapp.helper.mapIntoResult
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

interface IErrorRepository {
    fun getError(): Single<DummySuccessResponse>
}

@Singleton
class ErrorRepository @Inject constructor(
    private val api: ApiService
) : IErrorRepository {

    override fun getError(): Single<DummySuccessResponse> {
        return api.dummyErrorApi()
    }
}