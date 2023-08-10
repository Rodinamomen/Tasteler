package com.example.recipemobileapp.HomeActivity.favourites.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemobileapp.Authentication.Login.view.LoginFragment
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.HomeActivity.favourites.Repo.FavRepoImpl
import com.example.recipemobileapp.HomeActivity.favourites.adapter.FavsAdapter
import com.example.recipemobileapp.HomeActivity.favourites.viewModel.FavViewModel
import com.example.recipemobileapp.HomeActivity.favourites.viewModel.FavViewModelFactory
import com.example.recipemobileapp.Network.APIClient
import com.example.recipemobileapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FavouritesFragment : Fragment() {
    private lateinit var viewModel: FavViewModel
    private lateinit var recyclerViewFav:RecyclerView
    private lateinit var textViewEmpty: TextView
    private var loggedInUserId: Int = -1
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gettingViewModelReady()
        sharedPreferences = requireActivity().
            getSharedPreferences(LoginFragment.SHARED_PREFS, Context.MODE_PRIVATE)
        recyclerViewFav = view.findViewById(R.id.recyclerView_favourites)
        textViewEmpty = view.findViewById(R.id.textView_empty)
        textViewEmpty.visibility = View.VISIBLE
        loggedInUserId = sharedPreferences.getInt("userId",0)

        viewModel.getUserWithMeals()
        viewModel.userwithmeals.observe(viewLifecycleOwner){ userWithMeals->
            if(userWithMeals != null){
                for(item in userWithMeals){
                    if(item.user.userid == loggedInUserId && item.meals.isNotEmpty()){
                        textViewEmpty.visibility = View.GONE
                        addElements(item.meals,recyclerViewFav)
                    }
                }
            }
        }
    }
    private fun addElements(data:List<Meal>, recyclerView: RecyclerView){
        val mutableCopy = mutableListOf<Meal>().apply {
            addAll(data)
        }
        recyclerView.adapter = FavsAdapter(mutableCopy,viewModel
        ) { clickedMeal -> onRecipeClick(clickedMeal) }

        recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL, false)
    }

    private fun gettingViewModelReady(){
        val favFactory = FavViewModelFactory(FavRepoImpl(
            APIClient,
            LocalDataSourceImpl(requireContext())))
        viewModel = ViewModelProvider(this,favFactory)[FavViewModel::class.java]
    }
    private fun onRecipeClick(clickedMeal: Meal) {
        val bundle = Bundle()
        bundle.putParcelable("recipe", clickedMeal)
        findNavController().navigate(R.id.action_favouritesFragment_to_detailsFragment, bundle)
    }
}