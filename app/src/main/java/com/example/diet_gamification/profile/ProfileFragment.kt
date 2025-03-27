package com.example.diet_gamifikasi.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.diet_gamifikasi.R
import com.example.diet_gamifikasi.databinding.FragmentProfileBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        drawerLayout = binding.drawerLayout
        navView = binding.navView
        auth = FirebaseAuth.getInstance()

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        setupDrawerNavigation()
        observeUserData()
        setupLogoutButton()

        return binding.root
    }

    private fun setupDrawerNavigation() {
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_login -> {
                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                }
                R.id.nav_bmi_calculator -> {
                    findNavController().navigate(R.id.action_profileFragment_to_bmiCalculatorFragment)
                }
                R.id.nav_shop -> {
                    findNavController().navigate(R.id.action_profileFragment_to_shopFragment)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun observeUserData() {
        userViewModel.username.observe(viewLifecycleOwner) { name ->
            binding.tvUsername.text = name
        }

        userViewModel.exp.observe(viewLifecycleOwner) { exp ->
            binding.tvExp.text = "EXP: $exp"
        }
    }

    private fun setupLogoutButton() {
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
