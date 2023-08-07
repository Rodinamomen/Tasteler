package com.example.recipemobileapp.HomeActivity.favourites.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.HomeActivity.favourites.viewModel.FavViewModel
import com.example.recipemobileapp.HomeActivity.favourites.viewModel.FavviewModelFactory
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepoImpl
import com.example.recipemobileapp.HomeActivity.home.adapters.MainAdapter
import com.example.recipemobileapp.Network.APIClient
import com.example.recipemobileapp.R
import com.example.recipemobileapp.ViewModel.MealViewModel
import com.example.recipemobileapp.ViewModel.MealviewModelFactory

class FavouritesFragment : Fragment() {
    private lateinit var viewModel: FavViewModel
    private lateinit var recyclerViewFav:RecyclerView
    private lateinit var textViewEmpty: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gettingViewModelReady()
        recyclerViewFav = view.findViewById(R.id.recyclerView_favourites)
        textViewEmpty = view.findViewById(R.id.textView_empty)
        textViewEmpty.visibility = View.VISIBLE


//        viewModel.getMealsByUserId(1)

        viewModel.favList.observe(viewLifecycleOwner){ data->
            if(data != null){
                textViewEmpty.visibility = View.GONE
                addElements(data.meals,recyclerViewFav)
            }else{
                textViewEmpty.visibility = View.VISIBLE
            }
        }
    }
    private fun addElements(data:List<Meal>, recyclerView: RecyclerView){
        recyclerView.adapter = MainAdapter(data,this::onRecipeClick){ position ->
            val clickedMeal = data[position]
            viewModel.insertFav(Wishlist(1,clickedMeal.mealid))
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL, false)
    }
    private fun gettingViewModelReady(){
        val favFactory = FavviewModelFactory(
            MealRepoImpl(
                APIClient,
                LocalDataSourceImpl(requireContext())
            )
        )
        viewModel = ViewModelProvider(this,favFactory)[FavViewModel::class.java]
    }
    private fun onRecipeClick(clickedMeal: Meal) {
        val bundle = Bundle()
        bundle.putInt("recipeId", clickedMeal.mealid)
        findNavController().navigate(R.id.action_favouritesFragment_to_detailsFragment, bundle)
    }
}