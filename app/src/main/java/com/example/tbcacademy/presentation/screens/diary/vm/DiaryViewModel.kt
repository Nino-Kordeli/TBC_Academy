package com.example.tbcacademy.presentation.screens.diary.vm

import com.example.tbcacademy.domain.model.food.MealType
import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.screens.diary.contract.DiaryEvent
import com.example.tbcacademy.presentation.screens.diary.contract.DiaryState

class DiaryViewModel : BaseViewModel<DiaryState, DiaryEvent, Nothing>(DiaryState()) {

    override fun onEvent(event: DiaryEvent) {
        when (event) {
            is DiaryEvent.AddFood -> {
                updateState { current ->
                    when (event.mealType) {
                        MealType.BREAKFAST ->
                            current.copy(
                                breakfast = current.breakfast + event.food
                            )

                        MealType.LUNCH ->
                            current.copy(
                                lunch = current.lunch + event.food
                            )

                        MealType.DINNER ->
                            current.copy(
                                dinner = current.dinner + event.food
                            )

                        MealType.SNACKS ->
                            current.copy(
                                snacks = current.snacks + event.food
                            )
                    }
                }
            }
        }
    }
}