package com.example.recipemobileapp.HomeActivity.favourites.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipemobileapp.HomeActivity.favourites.Repo.FavRepo

class FavViewModelFactory(val favRepo: FavRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            FavViewModel(favRepo) as T
        }else{
            throw IllegalArgumentException("FavViewModel class not found")
        }
    }
}