package com.example.recipemobileapp.HomeActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.recipemobileapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.security.AccessController.getContext

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        val navView: BottomNavigationView = findViewById(R.id.bottomnavigationbar)
        navView.background = null
        navView.selectedItemId = R.id.placeholder
        navView.setupWithNavController(navController)
        val fab:FloatingActionButton = findViewById(R.id.fab_home)
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_home_filled))
        fab.setOnClickListener {
            navController.navigate(R.id.homeFragment)
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_home_filled))
        }
    }
}