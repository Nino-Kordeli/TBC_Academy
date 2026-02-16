package com.example.data.local

import android.content.Context
import android.icu.util.Calendar
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.model.food.LoggedFood
import com.example.domain.model.food.MealType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

private val Context.userPrefsDataStore by preferencesDataStore("user_preferences")

@Singleton
class UserPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Json
) {
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
    }

    suspend fun saveGoalCalories(calories: Int) {
        dataStore.edit { prefs ->
            prefs[GOAL_CALORIES] = calories
        }
    }

    fun getGoalCalories(): Flow<Int> {
        return dataStore.data.map { prefs ->
            prefs[GOAL_CALORIES] ?: 2000
        }
    }

    suspend fun saveUserProfile(
        email: String,
        name: String? = null,
        weight: String? = null,
        height: String? = null,
        age: Int? = null,
        gender: String? = null
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

    fun getUserMail(): Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[USER_EMAIL]
        }

    }

    suspend fun checkAndResetDailyData() {
        val lastResetDate = dataStore.data.first()[LAST_RESET_DATE] ?: 0L
        val todayStart = getTodayStartTime()

        if (lastResetDate < todayStart) {
            val list = listOf(BREAKFAST_FOODS, LUNCH_FOODS, DINNER_FOODS, SNACKS_FOODS)

            dataStore.edit { prefs ->
                list.forEach { key ->
                    prefs[key] = ""
                    prefs[LAST_RESET_DATE] = System.currentTimeMillis()
                }
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

    fun getFoodsForMeal(mealType: MealType): Flow<List<LoggedFood>> {
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

    suspend fun clearOnlyDailyFoodLogs() {
        val list = listOf(BREAKFAST_FOODS, LUNCH_FOODS, DINNER_FOODS, SNACKS_FOODS)
        dataStore.edit { prefs ->
            list.forEach { key ->
                prefs[key] = ""
            }
        }
    }

    suspend fun clearAllUserData() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}