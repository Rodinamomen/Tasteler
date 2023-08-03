package com.example.recipemobileapp.HomeActivity.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepoImpl
import com.example.recipemobileapp.HomeActivity.home.adapters.MainAdapter
import com.example.recipemobileapp.Network.APIClient
import com.example.recipemobileapp.R
import com.example.recipemobileapp.ViewModel.MealViewModel
import com.example.recipemobileapp.ViewModel.MealviewModelFactory

class HomeFragment : Fragment() {
    private lateinit var viewModel:MealViewModel
    private lateinit var data:List<Meal>
    private lateinit var recyclerViewRandomMeal: RecyclerView
    private lateinit var recyclerViewAllMeals: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()
        recyclerViewRandomMeal = view.findViewById(R.id.recyclerView_randomMeal)
        recyclerViewAllMeals = view.findViewById(R.id.recyclerView_home)
        viewModel.getRandomMeal()
        viewModel.getMealsList()
        viewModel.randomMealList.observe(viewLifecycleOwner){ meals->
            data = meals
            recyclerViewRandomMeal.adapter = MainAdapter(data)
            recyclerViewRandomMeal.layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.HORIZONTAL, false)
        }
        viewModel.mealList.observe(viewLifecycleOwner){ meals->
            data = meals
            recyclerViewAllMeals.adapter = MainAdapter(data)
            recyclerViewAllMeals.layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.HORIZONTAL, false)
        }
    }
    private fun initializeViewModel(){
        val mealFactory = MealviewModelFactory(MealRepoImpl(APIClient))
        viewModel = ViewModelProvider(this,mealFactory)[MealViewModel::class.java]
    }
}