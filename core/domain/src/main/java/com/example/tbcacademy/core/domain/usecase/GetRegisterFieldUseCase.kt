package com.example.tbcacademy.core.domain.usecase

import com.example.tbcacademy.core.domain.common.Resource
import com.example.tbcacademy.core.domain.model.Field
import com.example.tbcacademy.core.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRegisterFieldsUseCase @Inject constructor(
    private val repository: RegisterRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<Field>>> {
        return repository.getRegisterFields()
    }
}
