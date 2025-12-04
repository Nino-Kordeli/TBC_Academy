package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.common.HandleResponse
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.data.local.dao.UserDao
import com.example.tbcacademy.data.mapper.UsersMapper
import com.example.tbcacademy.data.mapper.base.asResource
import com.example.tbcacademy.data.remote.service.UsersService
import com.example.tbcacademy.domain.model.User
import com.example.tbcacademy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val usersService: UsersService,
    private val userDao: UserDao
) : UserRepository {
    override suspend fun fetchUsers(): Flow<Resource<Unit>> {
        return handleResponse.apiCall { usersService.fetchUsers() }.asResource { dtoList ->
            userDao.insertAll(UsersMapper.dtoListToEntity(dtoList))
        }
    }

    override suspend fun observeUsers(): Flow<List<User>> {
        return userDao.observeAll().map { entities ->
            UsersMapper.entityListToDomainList(entities)
        }
    }
}