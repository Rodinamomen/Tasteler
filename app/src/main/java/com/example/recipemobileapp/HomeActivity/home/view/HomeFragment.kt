package com.example.recipemobileapp.HomeActivity.home.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepoImpl
import com.example.recipemobileapp.HomeActivity.home.adapters.MainAdapter
import com.example.recipemobileapp.Network.APIClient
import com.example.recipemobileapp.R
import com.example.recipemobileapp.ViewModel.MealViewModel
import com.example.recipemobileapp.ViewModel.MealviewModelFactory

class HomeFragment : Fragment() {
    private lateinit var viewModel:MealViewModel
    private lateinit var recyclerViewRandomMeal: RecyclerView
    private lateinit var recyclerViewAllMeals: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        gettingViewModelReady()
        recyclerViewRandomMeal = view.findViewById(R.id.recyclerView_randomMeal)
        recyclerViewAllMeals = view.findViewById(R.id.recyclerView_home)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewRandomMeal = view.findViewById(R.id.recyclerView_randomMeal)
        recyclerViewAllMeals = view.findViewById(R.id.recyclerView_home)

        val processBarMeal:ProgressBar = view.findViewById(R.id.progresBar_allMeals)
        val processBarRandomMeal:ProgressBar = view.findViewById(R.id.progressBar_randomMeal)

        viewModel.getRandomMeal()
        viewModel.getMealsList(('A'..'Z').random())


        viewModel.randomMealList.observe(viewLifecycleOwner){ meals->
            if(meals != null){
                processBarRandomMeal.visibility = View.GONE
                addElements(meals,recyclerViewRandomMeal)
            }else{
                processBarRandomMeal.visibility = View.VISIBLE
            }
        }
        viewModel.mealList.observe(viewLifecycleOwner){ meals->
            if(meals != null){
                processBarMeal.visibility = View.GONE
                addElements(meals,recyclerViewAllMeals)
            }else{
                processBarMeal.visibility = View.VISIBLE
            }
        }

    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//
//    }
    private fun addElements(data:List<Meal>, recyclerView: RecyclerView){
        recyclerView.adapter = MainAdapter(data,{clickedMeal -> onRecipeClick(clickedMeal)}){ position ->
            val clickedMeal = data[position]
            Toast.makeText(requireContext(),"Added to Favs", Toast.LENGTH_SHORT).show()
            Log.d("TAG", "addElements: ${data[position]}")
            viewModel.insertFav(Wishlist(1, clickedMeal.mealid))
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.HORIZONTAL, false)
    }
    private fun gettingViewModelReady(){
        val mealFactory = MealviewModelFactory(MealRepoImpl(APIClient,LocalDataSourceImpl(requireContext())))
        viewModel = ViewModelProvider(this,mealFactory)[MealViewModel::class.java]
    }
    private fun onRecipeClick(clickedMeal: Meal) {
        val bundle = Bundle()
        bundle.putInt("recipeId", clickedMeal.mealid)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
    }
}