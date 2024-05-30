package com.example.noteapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.noteapp.R
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.model.SharedPreferenceHelper

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        sharedPreferenceHelper = SharedPreferenceHelper(this)

        if (!sharedPreferenceHelper.isOnBoardingComplete()) {
            sharedPreferenceHelper.setOnBoardingComplete(true)
        } else {
            navController.navigate(R.id.singUpFragment)
        }
    }
}