package com.example.recipemobileapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipemobileapp.HomeActivity.Repo.MealRepo

class MealviewModelFactory(val mealRepo: MealRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MealViewModel::class.java)) {
            MealViewModel(mealRepo) as T
        }else{
            throw IllegalArgumentException("mealViewModel class not found")
        }
    }
}