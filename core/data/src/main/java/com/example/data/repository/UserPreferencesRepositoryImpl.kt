package com.example.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.model.food.LoggedFood
import com.example.model.MealType
import com.example.domain.repository.UserPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

private val Context.userPrefsDataStore by preferencesDataStore("user_preferences")

@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val json: Json
) : UserPreferencesRepository {

    private val dataStore = context.userPrefsDataStore

    companion object {
        private val GOAL_CALORIES = intPreferencesKey("goal_calories")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_WEIGHT = stringPreferencesKey("user_weight")
        private val USER_HEIGHT = stringPreferencesKey("user_height")
        private val USER_AGE = intPreferencesKey("user_age")
        private val USER_GENDER = stringPreferencesKey("user_gender")

        private val BREAKFAST_FOODS = stringPreferencesKey("breakfast_foods")
        private val LUNCH_FOODS = stringPreferencesKey("lunch_foods")
        private val DINNER_FOODS = stringPreferencesKey("dinner_foods")
        private val SNACKS_FOODS = stringPreferencesKey("snacks_foods")
        private val LAST_RESET_DATE = longPreferencesKey("last_reset_date")
        private val CURRENT_USER_ID = stringPreferencesKey("current_user_id")
    }

    override suspend fun saveGoalCalories(calories: Int) {
        val userId = getCurrentUserId().first() ?: return
        dataStore.edit { prefs ->
            prefs[goalCaloriesKey(userId)] = calories
        }
    }

    override suspend fun setCurrentUserId(userId: String?) {
        dataStore.edit { prefs ->
            if (userId == null) {
                prefs.remove(CURRENT_USER_ID)
            } else {
                prefs[CURRENT_USER_ID] = userId
            }
        }
    }

    private fun goalCaloriesKey(userId: String) =
        intPreferencesKey("goal_calories_$userId")

    override fun getGoalCalories(): Flow<Int> {
        return getCurrentUserId().flatMapLatest { userId ->
            if (userId == null) {
                flowOf(2000)
            } else {
                dataStore.data.map { prefs ->
                    prefs[goalCaloriesKey(userId)] ?: 2000
                }
            }
        }
    }

    override suspend fun saveUserProfile(
        email: String,
        name: String?,
        weight: String?,
        height: String?,
        age: Int?,
        gender: String?
    ) {
        dataStore.edit { prefs ->
            prefs[USER_EMAIL] = email
            name?.let { prefs[USER_NAME] = it }
            weight?.let { prefs[USER_WEIGHT] = it }
            height?.let { prefs[USER_HEIGHT] = it }
            age?.let { prefs[USER_AGE] = it }
            gender?.let { prefs[USER_GENDER] = it }
        }
    }

    override fun getUserEmail(): Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[USER_EMAIL]
        }
    }

    override suspend fun checkAndResetDailyData() {
        val lastResetDate = dataStore.data.first()[LAST_RESET_DATE] ?: 0L
        val todayStart = getTodayStartTime()

        if (lastResetDate < todayStart) {
            val userId = getCurrentUserId().first() ?: return

            val mealKeys = listOf(
                MealType.BREAKFAST to BREAKFAST_FOODS,
                MealType.LUNCH to LUNCH_FOODS,
                MealType.DINNER to DINNER_FOODS,
                MealType.SNACKS to SNACKS_FOODS
            )

            mealKeys.forEach { (mealType, _) ->
                val allFoods = getFoodsForMeal(mealType).first()
                val otherUsersFoods = allFoods.filter { it.userId != userId }
                saveFoodsForMeal(mealType, otherUsersFoods)
            }

            dataStore.edit { prefs ->
                prefs[LAST_RESET_DATE] = System.currentTimeMillis()
            }
        }
    }

    private fun getTodayStartTime(): Long {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    override suspend fun saveFoodsForMeal(mealType: MealType, foods: List<LoggedFood>) {
        val key = when (mealType) {
            MealType.BREAKFAST -> BREAKFAST_FOODS
            MealType.LUNCH -> LUNCH_FOODS
            MealType.DINNER -> DINNER_FOODS
            MealType.SNACKS -> SNACKS_FOODS
        }

        dataStore.edit { prefs ->
            prefs[key] = json.encodeToString<List<LoggedFood>>(foods)
        }
    }

    override fun getFoodsForMeal(mealType: MealType): Flow<List<LoggedFood>> {
        val key = when (mealType) {
            MealType.BREAKFAST -> BREAKFAST_FOODS
            MealType.LUNCH -> LUNCH_FOODS
            MealType.DINNER -> DINNER_FOODS
            MealType.SNACKS -> SNACKS_FOODS
        }

        return dataStore.data.map { prefs ->
            val jsonString = prefs[key]
            if (jsonString.isNullOrBlank()) {
                emptyList()
            } else {
                try {
                    json.decodeFromString<List<LoggedFood>>(jsonString)
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }
    }

    override suspend fun addFood(
        mealType: MealType,
        food: LoggedFood
    ) {
        val current = getFoodsForMeal(mealType).first()
        saveFoodsForMeal(mealType, current + food)
    }

    override suspend fun clearOnlyDailyFoodLogs() {
        val list = listOf(BREAKFAST_FOODS, LUNCH_FOODS, DINNER_FOODS, SNACKS_FOODS)
        dataStore.edit { prefs ->
            list.forEach { key -> prefs[key] = "" }
            prefs[LAST_RESET_DATE] = System.currentTimeMillis()
        }
    }

    override suspend fun clearAllUserData() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    override fun getCurrentUserId(): Flow<String?> = dataStore.data.map { prefs ->
        prefs[CURRENT_USER_ID]
    }
}