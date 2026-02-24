package com.example.impl.screens.vm

import androidx.lifecycle.viewModelScope
import com.example.common.resource.Resource
import com.example.domain.usecase.exercise.LogExerciseUseCase
import com.example.domain.usecase.workout.GetWorkoutsUseCase
import com.example.impl.screens.contract.WorkoutEvent
import com.example.impl.screens.contract.WorkoutSideEffect
import com.example.impl.screens.contract.WorkoutState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val getWorkoutsUseCase: GetWorkoutsUseCase,
    private val logExerciseUseCase: LogExerciseUseCase
) : BaseViewModel<WorkoutState, WorkoutEvent, WorkoutSideEffect>(WorkoutState()) {

    init {
        getWorkouts()
    }

    override fun onEvent(event: WorkoutEvent) {
        when (event) {
            is WorkoutEvent.LogExercise -> logExercise(event.exerciseId, event.calories)
        }
    }

    private fun getWorkouts() {
        viewModelScope.launch {
            getWorkoutsUseCase.invoke().collect { resource ->
                when (resource) {
                    is Resource.Loading -> updateState { it.copy(isLoading = resource.loading) }
                    is Resource.Error -> updateState {
                        it.copy(
                            error = resource.errorMessage,
                            isLoading = false
                        )
                    }

                    is Resource.Success -> updateState {
                        it.copy(
                            categories = resource.data,
                            isLoading = false,
                            error = null
                        )
                    }
                }
            }
        }
    }

    private fun logExercise(exerciseId: String, calories: Int) {
        viewModelScope.launch {
            logExerciseUseCase(calories)
            updateState { it.copy(loggedExerciseIds = it.loggedExerciseIds + exerciseId) }
            emitSideEffect(WorkoutSideEffect.ShowToast("+$calories kcal burned logged!"))
        }
    }
}