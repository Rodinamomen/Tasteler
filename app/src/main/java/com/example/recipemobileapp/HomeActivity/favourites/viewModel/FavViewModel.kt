package com.example.recipemobileapp.HomeActivity.favourites.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Userwithmeals
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.HomeActivity.favourites.Repo.FavRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavViewModel(val favRepo: FavRepo):ViewModel() {

    private val _userWithMeal = MutableLiveData<List<Userwithmeals>>()
    val userwithmeals:LiveData<List<Userwithmeals>> = _userWithMeal

    fun deleteMeal(meal:Meal){
        viewModelScope.launch(Dispatchers.IO) {
            favRepo.deleteMeal(meal)
        }
    }

    fun getUserWithMeals(){
        viewModelScope.launch {
            val response = favRepo.getuserWithMeals()
            _userWithMeal.value = response
        }
    }
    fun deleteWishlist(wishlist: Wishlist){
        viewModelScope.launch(Dispatchers.IO) {
            favRepo.deleteWishlist(wishlist)
        }
    }
}