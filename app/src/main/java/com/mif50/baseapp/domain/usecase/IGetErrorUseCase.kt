package com.mif50.baseapp.domain.usecase

import com.mif50.baseapp.data.network.response.DummySuccessResponse
import com.mif50.baseapp.domain.repository.ErrorRepository
import com.mif50.baseapp.domain.repository.IErrorRepository
import com.mif50.baseapp.domain.repository.TransactionRepository
import com.mif50.baseapp.helper.ResultWrapper
import com.mif50.baseapp.helper.SimpleResult
import com.mif50.baseapp.helper.mapIntoResult
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

interface IGetErrorUseCase {
    operator fun invoke(): Single<DummySuccessResponse>
}

@Singleton
class GetErrorUseCase @Inject constructor(
    private val repository: ErrorRepository
) : IGetErrorUseCase {

    override fun invoke(): Single<DummySuccessResponse> {
        return repository.getError()
    }
}