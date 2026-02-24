package com.example.data.repository

import com.example.common.mapper.asResource
import com.example.common.resource.Resource
import com.example.data.mapper.workout.toDomain
import com.example.data.remote.workout.WorkoutApi
import com.example.data.response_handler.retrofit.HandleRetrofitResponse
import com.example.domain.model.workout.WorkoutCategory
import com.example.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val workoutApi: WorkoutApi,
    private val handleResponse: HandleRetrofitResponse
) : WorkoutRepository {

    override suspend fun getWorkouts(): Flow<Resource<List<WorkoutCategory>>> {
        return handleResponse.apiCall { workoutApi.getWorkouts() }.asResource { it.toDomain() }
    }
}