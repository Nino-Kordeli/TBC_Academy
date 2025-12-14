package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.local.entity.LocationEntity
import com.example.tbcacademy.data.remote.dto.LocationDto
import com.example.tbcacademy.domain.model.Location

fun LocationDto.toEntity(): LocationEntity {
    return LocationEntity(
        id = id,
        title = title,
        description = description,
        latitude = latitude,
        longitude = longitude,
        imageUrl = imageUrl
    )
}

fun LocationEntity.toDomain(): Location {
    return Location(
        id = id,
        title = title,
        description = description,
        latitude = latitude,
        longitude = longitude,
        imageUrl = imageUrl
    )
}

fun LocationDto.toDomain(): Location {
    return Location(
        id = id,
        title = title,
        description = description,
        latitude = latitude,
        longitude = longitude,
        imageUrl = imageUrl
    )
}

fun List<LocationDto>.dtoListToEntities(): List<LocationEntity> {
    return map { it.toEntity() }
}

fun List<LocationEntity>.entityToDomain(): List<Location> {
    return map { it.toDomain() }
}

fun List<LocationDto>.dtoToDomain(): List<Location> {
    return map { it.toDomain() }
}