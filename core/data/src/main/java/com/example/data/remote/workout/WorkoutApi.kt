package com.example.data.remote.workout

import com.example.data.dto.workout.WorkoutResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface WorkoutApi {
    @GET("workouts")
    suspend fun getWorkouts(): Response<WorkoutResponseDto>
}