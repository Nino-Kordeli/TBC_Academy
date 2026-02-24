package com.example.data.repository

import com.example.common.resource.Resource
import com.example.data.mapper.toDomain
import com.example.data.remote.FoodApi
import com.example.domain.model.workout.WorkoutCategory
import com.example.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val foodApi: FoodApi,
) : WorkoutRepository {

    override fun getWorkouts(): Flow<Resource<List<WorkoutCategory>>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = foodApi.getWorkouts()
            if (response.isSuccessful) {
                val categories = response.body()!!.categories.map { it.toDomain() }
                emit(Resource.Loading(false))
                emit(Resource.Success(categories))
            } else {
                emit(Resource.Loading(false))
                emit(Resource.Error("Failed to load workouts: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Loading(false))
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}