package com.example.tbcacademy.core.domain.usecase

import com.example.tbcacademy.domain.model.Field
import com.example.tbcacademy.core.domain.repository.RegisterRepository
import javax.inject.Inject

class GetRegisterFieldsUseCase @Inject constructor(
    private val repository: RegisterRepository
) {

    suspend operator fun invoke(): List<Field> {
        return repository.getRegisterFields()
    }
}
