package com.example.recipemobileapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Repo.MealRepo
import kotlinx.coroutines.launch

class
MealViewModel(val mealRepo: MealRepo):ViewModel() {
    private val _mealList = MutableLiveData<List<Meal>>()
    val mealList: LiveData<List<Meal>> = _mealList

    fun getMealsList(){
        viewModelScope.launch {
            val response = mealRepo.getMealFromAPI()
            _mealList.value =response.meals
        }
    }
}