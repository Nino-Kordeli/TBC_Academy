package com.example.domain.repository

import com.example.common.resource.Resource
import com.example.domain.model.workout.WorkoutCategory
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    suspend fun getWorkouts(): Flow<Resource<List<WorkoutCategory>>>
}