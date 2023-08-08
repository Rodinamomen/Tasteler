package com.example.recipemobileapp.HomeActivity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.recipemobileapp.Authentication.Login.view.LoginFragment
import com.example.recipemobileapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.security.AccessController.getContext

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




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.option_menu,menu)
        return true}


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        sharedPreferences= getSharedPreferences(
            LoginFragment.SHARED_PREFS,
            Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()
        when(item.itemId){
            R.id.aboutFragment ->{
                val navController = findNavController(R.id.nav_host)
                navController.navigate(R.id.aboutFragment) }


            else -> { sharedPreferences= getSharedPreferences(
                LoginFragment.SHARED_PREFS,
                Context.MODE_PRIVATE)
                editor=sharedPreferences.edit()
                editor.remove(EMAIL_KEY)
                editor.remove(PASSWORD_KEY)
                editor.commit()
                navController.navigate(R.id.aucthenticationActivity)
                finish()
            } }

        return super.onOptionsItemSelected(item)}

}