package com.example.recipemobileapp.Authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.recipemobileapp.R
import com.google.android.material.navigation.NavigationView


class AucthenticationActivity : AppCompatActivity() {
    lateinit var navigationView: NavigationView
    lateinit var navhost: NavHostFragment
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aucthentication)


    }
}