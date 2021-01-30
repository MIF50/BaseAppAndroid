package com.mif50.baseapp.domain.repository

import com.mif50.baseapp.data.network.ApiService
import com.mif50.baseapp.data.network.response.DummySuccessResponse
import com.mif50.baseapp.helper.SimpleResult
import com.mif50.baseapp.helper.mapIntoResult
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

interface ITransactionRepository {
    fun getTransaction(): Single<DummySuccessResponse>
}

@Singleton
class TransactionRepository @Inject constructor(
    private val api: ApiService
) : ITransactionRepository {

    override fun getTransaction(): Single<DummySuccessResponse> {
        return api.dummySuccessApi()
    }
}