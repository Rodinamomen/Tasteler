package com.example.recipemobileapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipemobileapp.HomeActivity.home.Repo.SearchRepo

class SearchViewModelFactory(val searchRepo: SearchRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel(searchRepo) as T
        }else{
            throw IllegalArgumentException("SearchViewModel class not found")
        }
    }
}