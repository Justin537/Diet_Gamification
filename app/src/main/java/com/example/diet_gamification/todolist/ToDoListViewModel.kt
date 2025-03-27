package com.example.diet_gamification.todolist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diet_gamification.todolist.FoodEntry

class ToDoListViewModel : ViewModel() {
    val totalCalories = MutableLiveData(0)
    val calorieGoal = MutableLiveData(2000)

    val breakfastFoods = MutableLiveData<List<FoodEntry>>(emptyList())
    val lunchFoods = MutableLiveData<List<FoodEntry>>(emptyList())
    val dinnerFoods = MutableLiveData<List<FoodEntry>>(emptyList())

    private val mealHistory = mutableMapOf<String, Triple<List<FoodEntry>, List<FoodEntry>, List<FoodEntry>>>()

    fun addFoodToMeal(meal: String, food: FoodEntry) {
        when (meal) {
            "Breakfast" -> breakfastFoods.value = breakfastFoods.value?.plus(food)
            "Lunch" -> lunchFoods.value = lunchFoods.value?.plus(food)
            "Dinner" -> dinnerFoods.value = dinnerFoods.value?.plus(food)
        }
        totalCalories.value = (totalCalories.value ?: 0) + food.calories
    }

    fun loadMealsForDate(date: String) {
        val meals = mealHistory[date] ?: Triple(emptyList(), emptyList(), emptyList())
        breakfastFoods.value = meals.first
        lunchFoods.value = meals.second
        dinnerFoods.value = meals.third
    }

    fun saveMealsForDate(date: String) {
        mealHistory[date] = Triple(
            breakfastFoods.value ?: emptyList(),
            lunchFoods.value ?: emptyList(),
            dinnerFoods.value ?: emptyList()
        )
    }
}

