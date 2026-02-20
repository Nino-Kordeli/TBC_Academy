package com.example.data.repository

//import androidx.datastore.dataStore
//import com.example.domain.model.food.LoggedFood
//import com.example.model.MealType
//import com.example.domain.repository.DiaryRepository
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject
//
//class DiaryRepositoryImpl @Inject constructor(
//    private val datastore: DiaryDataStore
//) : DiaryRepository {
//    override fun getFoodsForMeal(mealType: MealType): Flow<List<LoggedFood>> =
//        dataStore.observeMeal(mealType)
//
//    override suspend fun addFood(
//        mealType: MealType,
//        food: LoggedFood
//    ) {
//       datastore.addFood(mealType,food)
//    }
//
//    override suspend fun removeFood(
//        mealType: MealType,
//        food: LoggedFood
//    ) {
//        datastore.removeFood(mealType,food)
//    }
//
//    override suspend fun resetDailyData() {
//        datastore.clearAllMeals()
//    }
//}