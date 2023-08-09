package com.example.recipemobileapp.HomeActivity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.recipemobileapp.Authentication.Login.view.LoginFragment
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL_KEY = "email_key"
        const val PASSWORD_KEY = "password_key"
    }
    lateinit var editor: SharedPreferences.Editor
    lateinit var sharedPreferences: SharedPreferences
    lateinit var navHostFragment : NavHostFragment
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
         navController = navHostFragment.navController
        sharedPreferences = getSharedPreferences(LoginFragment.SHARED_PREFS,
            Context.MODE_PRIVATE)
        val navView: BottomNavigationView = findViewById(R.id.bottomnavigationbar)
        val bottomAppbar:CoordinatorLayout = findViewById(R.id.coordinatorLayout_home)
        val email = sharedPreferences.getString("email_key","")!!


        navView.background = null
        navView.selectedItemId = R.id.placeholder
        navView.setupWithNavController(navController)
        val fab:FloatingActionButton = findViewById(R.id.fab_home)

        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_home_filled))
        fab.setOnClickListener {
            navController.navigate(R.id.homeFragment)
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_home_filled))
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.detailsFragment) {
                bottomAppbar.visibility = View.GONE

            } else {
                bottomAppbar.visibility = View.VISIBLE
            }
        }
    }

}