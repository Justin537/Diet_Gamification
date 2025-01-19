package com.example.diet_gamification.todolist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diet_gamification.todolist.FoodEntry

class ToDoListViewModel : ViewModel() {
    val calorieGoal = 2000
    var totalCalories by mutableStateOf(0)

    val breakfastFoods = mutableStateListOf<FoodEntry>()
    val lunchFoods = mutableStateListOf<FoodEntry>()
    val dinnerFoods = mutableStateListOf<FoodEntry>()

    fun addFoodToMeal(meal: String, food: FoodEntry) {
        when (meal) {
            "Breakfast" -> breakfastFoods.add(food)
            "Lunch" -> lunchFoods.add(food)
            "Dinner" -> dinnerFoods.add(food)
        }
        totalCalories += food.calories
    }
}
