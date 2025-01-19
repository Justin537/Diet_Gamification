import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diet_gamification.todolist.FoodEntry
import com.example.diet_gamification.todolist.ToDoListViewModel

@Composable
fun ToDoListFragment(viewModel: ToDoListViewModel = viewModel()) {
    var selectedDay by remember { mutableStateOf("Today") }

    Scaffold(
        topBar = {
            Column {
                // Calorie Progress Bar
                Text("Calories Consumed Today: ${viewModel.totalCalories} / ${viewModel.calorieGoal}", modifier = Modifier.padding(16.dp))
                LinearProgressIndicator(
                    progress = viewModel.totalCalories / viewModel.calorieGoal.toFloat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .padding(horizontal = 16.dp)
                )

                // Navigation for Today/Tomorrow/Yesterday
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("Yesterday", "Today", "Tomorrow").forEach { day ->
                        ClickableText(
                            text = AnnotatedString(day),
                            onClick = { selectedDay = day },
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                // Display Tables for Meals
                MealSection("Breakfast", viewModel.breakfastFoods) { food ->
                    viewModel.addFoodToMeal("Breakfast", food)
                }
                MealSection("Lunch", viewModel.lunchFoods) { food ->
                    viewModel.addFoodToMeal("Lunch", food)
                }
                MealSection("Dinner", viewModel.dinnerFoods) { food ->
                    viewModel.addFoodToMeal("Dinner", food)
                }
            }
        }
    )
}

@Composable
fun MealSection(
    mealName: String,
    foodList: List<FoodEntry>,
    onAddFood: (FoodEntry) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "$mealName", style = MaterialTheme.typography.bodyLarge)
        LazyColumn {
            items(foodList) { food ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = food.name, modifier = Modifier.weight(1f))
                    Text(text = "${food.calories} cal", modifier = Modifier.weight(1f))
                }
            }
        }
        Button(onClick = { onAddFood(FoodEntry("New Food", 100)) }) {
            Text("Add Food")
        }
    }
}

// ViewModel for managing food data
class ToDoListViewModel : ViewModel() {
    var totalCalories by mutableStateOf(0)
    var calorieGoal by mutableStateOf(2000)

    val breakfastFoods = mutableListOf<FoodEntry>()
    val lunchFoods = mutableListOf<FoodEntry>()
    val dinnerFoods = mutableListOf<FoodEntry>()

    fun addFoodToMeal(meal: String, food: FoodEntry) {
        when (meal) {
            "Breakfast" -> breakfastFoods.add(food)
            "Lunch" -> lunchFoods.add(food)
            "Dinner" -> dinnerFoods.add(food)
        }
        // Update calorie count as an example
        totalCalories += food.calories
    }
}

data class FoodEntry(val name: String, val calories: Int)
