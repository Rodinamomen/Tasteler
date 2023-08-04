package com.example.recipemobileapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealViewModel(val mealRepo: MealRepo):ViewModel() {
    private val _mealList = MutableLiveData<List<Meal>>()
    val mealList: LiveData<List<Meal>> = _mealList
    private val _randomMealList = MutableLiveData<List<Meal>>()
    val randomMealList: LiveData<List<Meal>> = _randomMealList

    fun getMealsList(){
        viewModelScope.launch {
            val response = mealRepo.getAllMealsFromAPI()
            _mealList.value =response.meals
        }
    }
    fun getRandomMeal(){
        viewModelScope.launch {
            val response = mealRepo.getRandomMealFromAPI()
            _randomMealList.value = response.meals
        }
    }

}