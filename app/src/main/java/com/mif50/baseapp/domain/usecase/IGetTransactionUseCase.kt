package com.mif50.baseapp.domain.usecase

import com.mif50.baseapp.data.network.response.DummySuccessResponse
import com.mif50.baseapp.domain.repository.ITransactionRepository
import com.mif50.baseapp.domain.repository.TransactionRepository
import com.mif50.baseapp.helper.SimpleResult
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

interface IGetTransactionUseCase {
    operator fun invoke(): Single<DummySuccessResponse>
}

@Singleton
class GetTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) : IGetTransactionUseCase {

    override fun invoke(): Single<DummySuccessResponse> {
        return repository.getTransaction()
    }
}