package com.example.diet_gamifikasi

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.diet_gamification.R
import com.example.diet_gamifikasi.profile.UserViewModel
import com.example.diet_gamifikasi.todolist.ToDoListFragment
import com.example.diet_gamifikasi.workout.WorkOutFragment
import com.example.diet_gamifikasi.report.ReportFragment
import com.example.diet_gamifikasi.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_todo -> openFragment(ToDoListFragment())
                R.id.nav_workout -> openFragment(WorkOutFragment())
                R.id.nav_report -> openFragment(ReportFragment())
                R.id.nav_profile -> openFragment(ProfileFragment())
            }
            true
        }

        // Set default fragment
        if (savedInstanceState == null) {
            openFragment(ToDoListFragment())
        }

        // Observe user data
        userViewModel.username.observe(this, Observer { name ->
            findViewById<TextView>(R.id.tvUsername).text = name
        })

        userViewModel.exp.observe(this, Observer { exp ->
            findViewById<TextView>(R.id.tvExp).text = "EXP: $exp"
        })
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
