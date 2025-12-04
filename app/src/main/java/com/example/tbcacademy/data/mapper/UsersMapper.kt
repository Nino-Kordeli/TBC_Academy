package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.dto.UserResponseDto
import com.example.tbcacademy.data.local.entities.UserEntity
import com.example.tbcacademy.domain.model.User

object UsersMapper {

    fun dtoToEntity(dto: UserResponseDto) = UserEntity(
        id = dto.id,
        fullName = dto.fullName,
        email = dto.email,
        activationStatus = dto.activationStatus,
        lastActiveDescription = dto.lastActiveDescription,
        lastActiveEpoch = dto.lastActiveEpoch,
        profileImageUrl = dto.profileImageUrl
    )

    fun dtoListToEntity(dtoList: List<UserResponseDto>) = dtoList.map { dtoToEntity(it) }

    fun entityToDomain(entity: UserEntity) = User(
        id = entity.id,
        fullName = entity.fullName,
        email = entity.email,
        activationStatus = entity.activationStatus,
        lastActiveDescription = entity.lastActiveDescription,
        lastActiveEpoch = entity.lastActiveEpoch,
        profileImageUrl = entity.profileImageUrl
    )

    fun entityListToDomainList(list: List<UserEntity>) = list.map { entityToDomain(it) }
}