package com.example.bitirmeprojesi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.ActivityFeedBinding
import com.example.bitirmeprojesi.databinding.FragmentDetayBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHosFragment= supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        NavigationUI.setupWithNavController(binding.bottomNavigationView,navHosFragment.navController)
    }
}