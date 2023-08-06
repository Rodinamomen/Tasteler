package com.example.recipemobileapp.HomeActivity.favourites.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Userwithmeals
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavViewModel(val mealRepo: MealRepo):ViewModel() {
    private val _favList = MutableLiveData<Userwithmeals?>()
    val favList: LiveData<Userwithmeals?> = _favList


    fun getMealsByUserId(userID:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mealRepo.getFavouriteMealsWithUserId(userID)
            _favList.value = response
        }
    }

    fun insertFav(wishlist: Wishlist){
        viewModelScope.launch(Dispatchers.IO) {
            mealRepo.insertIntofavs(wishlist)
        }
    }
}