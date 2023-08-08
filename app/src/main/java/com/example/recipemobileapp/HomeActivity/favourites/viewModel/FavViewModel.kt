package com.example.recipemobileapp.HomeActivity.favourites.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.Userwithmeals
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.HomeActivity.favourites.Repo.FavRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavViewModel(val favRepo: FavRepo):ViewModel() {

    private val _userWithMeals = MutableLiveData<List<Userwithmeals>>()
    val userWithMeals: LiveData<List<Userwithmeals>> = _userWithMeals

    private val _loggedUser = MutableLiveData<User>()
    val loggedUser:LiveData<User> = _loggedUser

    private val _savedMeal = MutableLiveData<Meal>()
    val savedMeal:LiveData<Meal> = _savedMeal

    private val _userWithMeal = MutableLiveData<List<Userwithmeals>>()
    val userwithmeals:LiveData<List<Userwithmeals>> = _userWithMeal

    fun insertFav(wishlist: Wishlist){
        viewModelScope.launch(Dispatchers.IO) {
            favRepo.insertIntofavs(wishlist)
        }
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch(Dispatchers.IO) {
            favRepo.insertMeal(meal)
        }
    }
    fun deleteMeal(meal:Meal){
        viewModelScope.launch(Dispatchers.IO) {
            favRepo.deleteMeal(meal)
        }
    }

    fun getUserId(userEmail:String){
        viewModelScope.launch {
            val userResponse = favRepo.getUserIdByEmail(userEmail)
            _loggedUser.value = userResponse
        }
    }
    fun getMealId(mealId:String){
        viewModelScope.launch {
            val mealResponse =  favRepo.getMealById(mealId)
            _savedMeal.value = mealResponse
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