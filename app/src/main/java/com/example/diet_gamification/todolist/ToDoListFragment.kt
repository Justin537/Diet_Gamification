package com.example.diet_gamification.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diet_gamification.R
import com.example.diet_gamification.model.FoodItem

class ToDoListFragment : Fragment() {

    private lateinit var viewModel: ToDoListViewModel
    private lateinit var progressKcal: ProgressBar
    private lateinit var kcalText: TextView
    private lateinit var calendarView: CalendarView
    private lateinit var recyclerBreakfast: RecyclerView
    private lateinit var recyclerLunch: RecyclerView
    private lateinit var recyclerDinner: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_todolist, container, false)

        // Initialize UI components
        calendarView = view.findViewById(R.id.calendarView)
        recyclerBreakfast = view.findViewById(R.id.recycler_breakfast)
        recyclerLunch = view.findViewById(R.id.recycler_lunch)
        recyclerDinner = view.findViewById(R.id.recycler_dinner)
        progressKcal = view.findViewById(R.id.progress_kcal)
        kcalText = view.findViewById(R.id.text_kcal_count)

        // Set up RecyclerViews
        recyclerBreakfast.layoutManager = LinearLayoutManager(requireContext())
        recyclerLunch.layoutManager = LinearLayoutManager(requireContext())
        recyclerDinner.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(ToDoListViewModel::class.java)

        // Set up Adapters with click listeners
        val breakfastAdapter = FoodAdapter(emptyList()) { foodItem -> onFoodClicked(foodItem) }
        val lunchAdapter = FoodAdapter(emptyList()) { foodItem -> onFoodClicked(foodItem) }
        val dinnerAdapter = FoodAdapter(emptyList()) { foodItem -> onFoodClicked(foodItem) }

        recyclerBreakfast.adapter = breakfastAdapter
        recyclerLunch.adapter = lunchAdapter
        recyclerDinner.adapter = dinnerAdapter

        // Observe total calories
        viewModel.totalCalories.observe(viewLifecycleOwner) { updateCalories(it) }

        // Observe meal lists and update adapters
        viewModel.breakfastFoods.observe(viewLifecycleOwner) { breakfastAdapter.updateList(it) }
        viewModel.lunchFoods.observe(viewLifecycleOwner) { lunchAdapter.updateList(it) }
        viewModel.dinnerFoods.observe(viewLifecycleOwner) { dinnerAdapter.updateList(it) }

        // Handle calendar date change
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            viewModel.loadMealsForDate(selectedDate)
        }
    }

    private fun updateCalories(calories: Int) {
        progressKcal.progress = calories
        kcalText.text = "$calories / ${viewModel.calorieGoal.value ?: 0} kcal"
    }

    private fun onFoodClicked(foodItem: FoodItem) {
        // Handle food item click, e.g., show details or allow editing
    }
}
