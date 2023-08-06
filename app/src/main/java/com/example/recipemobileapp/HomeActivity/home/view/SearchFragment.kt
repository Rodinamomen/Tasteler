package com.example.recipemobileapp.HomeActivity.home.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepoImpl
import com.example.recipemobileapp.HomeActivity.home.adapters.MainAdapter
import com.example.recipemobileapp.Network.APIClient
import com.example.recipemobileapp.R
import com.example.recipemobileapp.ViewModel.MealViewModel
import com.example.recipemobileapp.ViewModel.MealviewModelFactory


class SearchFragment : Fragment() {

    private lateinit var viewModel: MealViewModel
    private lateinit var recyclerViewSearchMeal: RecyclerView
    private lateinit var searchView: SearchView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


    val view =inflater.inflate(R.layout.fragment_search, container, false)
        // Getting View Model Ready
        gettingViewModelReady()
        // Set Recyclerview value by ID
        recyclerViewSearchMeal = view.findViewById(R.id.recyclerViewSearchResults)
        searchView  =view.findViewById(R.id.searchView)


        return view }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewSearchMeal = view.findViewById(R.id.recyclerViewSearchResults)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                handleSearchQuery(query)
//                Toast.makeText(view.context,query,Toast.LENGTH_SHORT).show()
                return false }

            override fun onQueryTextChange(p0: String?): Boolean =false
        })



        viewModel.searchMealList.observe(viewLifecycleOwner){ meals->
            if(meals != null){ addElements(meals,recyclerViewSearchMeal) }

        }


    }
    private fun addElements(data:List<Meal>, recyclerView: RecyclerView){
        recyclerView.adapter = MainAdapter(data){ position ->
            val clickedMeal = data[position]
            Toast.makeText(requireContext(),"Added to Favs", Toast.LENGTH_SHORT).show()
            Log.d("TAG", "addElements: ${data[position]}")
            viewModel.insertFav(Wishlist(1, clickedMeal.mealid))
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL, false)
    }
    private fun gettingViewModelReady(){
        val mealFactory = MealviewModelFactory(MealRepoImpl(APIClient,
            LocalDataSourceImpl(requireContext())
        ))
        viewModel = ViewModelProvider(this,mealFactory)[MealViewModel::class.java]
    }
    private fun handleSearchQuery(query: String) { viewModel.getSearchResult(query) }

}