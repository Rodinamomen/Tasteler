package com.example.recipemobileapp.ViewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealViewModel(val mealRepo: MealRepo):ViewModel() {
    private val _mealList = MutableLiveData<List<Meal>>()
    val mealList: LiveData<List<Meal>> = _mealList
    private val _randomMealList = MutableLiveData<List<Meal>>()
    val randomMealList: LiveData<List<Meal>> = _randomMealList

    private val _sentmealtodetails = MutableLiveData<Meal>()
    val sentmealtodetails: LiveData<Meal> = _sentmealtodetails

    private val _searchMealList = MutableLiveData<List<Meal>>()
    val searchMealList: LiveData<List<Meal>> = _randomMealList


    fun getMealsList(randomChar: Char){

        viewModelScope.launch {
            try {
                val response = mealRepo.getAllMealsFromAPI(randomChar)
                _mealList.value =response.meals
            } catch (e: Exception) {
                Log.d("Connection", "getMealsList: No connection")
            }

        }
    }
    fun getRandomMeal(){
        viewModelScope.launch {
            try {
                val response = mealRepo.getRandomMealFromAPI()
                _randomMealList.value = response.meals
            } catch (e: Exception) {
                Log.d("Connection", "getMealsList: No connection in random")
            }
        }        }

    fun getSearchResult(search :String ){
        viewModelScope.launch {
            try {val response = mealRepo.getSearchResultFromAPI(search)
                _searchMealList.value = response.meals

            }catch (e: Exception) {
                Log.d("Connection", "getSearchResult: No connection in Search")
            }

        } }

    fun insertFav(wishlist: Wishlist){
        viewModelScope.launch(Dispatchers.IO) {
            mealRepo.insertIntofavs(wishlist)
        }
    }

    fun getMealbyID(ID: Int): LiveData<Meal> {
        val resultLiveData = MutableLiveData<Meal>()

        viewModelScope.launch {
            try {
                val meal = mealRepo.getMealByID(ID)
                resultLiveData.postValue(meal)

            } catch (e: Exception) {
                Log.e("MealViewModel", "Error getting meal by ID: ${e.message}")
            }
        }

        return resultLiveData
    }
}
