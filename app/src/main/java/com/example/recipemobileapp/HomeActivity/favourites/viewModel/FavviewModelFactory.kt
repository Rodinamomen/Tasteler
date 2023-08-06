package com.example.recipemobileapp.HomeActivity.favourites.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepo

class FavviewModelFactory(val mealRepo: MealRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            FavViewModel(mealRepo) as T
        }else{
            throw IllegalArgumentException("favViewModel class not found")
        }
    }
}