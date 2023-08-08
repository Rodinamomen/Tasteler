package com.example.recipemobileapp.HomeActivity.search.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemobileapp.Authentication.Login.view.LoginFragment
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.HomeActivity.home.Repo.SearchRepoImpl
import com.example.recipemobileapp.HomeActivity.home.adapters.MainAdapter
import com.example.recipemobileapp.Network.APIClient
import com.example.recipemobileapp.R
import com.example.recipemobileapp.ViewModel.SearchViewModel
import com.example.recipemobileapp.ViewModel.SearchViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var recyclerViewSearchMeal: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var sharedPreferences: SharedPreferences
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
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences(LoginFragment.SHARED_PREFS, Context.MODE_PRIVATE)


        // recyclerViewSearchMeal = view.findViewById(R.id.recyclerViewSearchResults)

//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                handleSearchQuery(query)
////                Toast.makeText(view.context,query,Toast.LENGTH_SHORT).show()
//                return false }
//
//            override fun onQueryTextChange(p0: String?): Boolean =false
//        })
//

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                handleSearchQuery(p0!!)

        return true}

            override fun onQueryTextChange(p0: String?): Boolean {
               handleSearchQuery(p0!!)
            return true}


        })

        viewModel.searchMealList.observe(viewLifecycleOwner){ meals->
            if(meals != null){ addElements(meals,recyclerViewSearchMeal) }
        }
        val combinedLiveData = MediatorLiveData<Pair<User?, Meal?>>()

        viewModel.loggedUser.observe(viewLifecycleOwner) { user ->
            combinedLiveData.value = Pair(user, combinedLiveData.value?.second)
        }

        viewModel.savedMeal.observe(viewLifecycleOwner) { meal ->
            combinedLiveData.value = Pair(combinedLiveData.value?.first, meal)
        }

        combinedLiveData.observe(viewLifecycleOwner) { (user, meal) ->
            if (user != null && meal != null) {
                Log.d("TAG", "Both user and meal data are available: $user, $meal")
                viewModel.insertFav(Wishlist(user.userid, meal.idMeal))
            }
        }

    }
    private fun addElements(data:List<Meal>, recyclerView: RecyclerView){
        recyclerView.adapter = MainAdapter(data,
            {clickedMeal -> onRecipeClick(clickedMeal)})
            { position ->
                    val clickedMeal = data[position]
                    Toast.makeText(requireContext(),"Added to Favs", Toast.LENGTH_SHORT).show()
                    viewModel.insertMeal(clickedMeal)
                    val email = sharedPreferences.getString("email_key","")!!
                    viewModel.getUserId(email)
                    viewModel.getMealId(clickedMeal.idMeal)
                    Log.d("TAG", "addElements: $email ${clickedMeal.idMeal}")
            }
    }

    private fun gettingViewModelReady(){
        val searchFactory = SearchViewModelFactory(
            SearchRepoImpl(APIClient,
            LocalDataSourceImpl(requireContext()))
        )
        viewModel = ViewModelProvider(this,searchFactory)[SearchViewModel::class.java]
    }
    private fun onRecipeClick(clickedMeal: Meal) {
        val bundle = Bundle()
        bundle.putParcelable("recipe", clickedMeal)
        findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, bundle)
    }
    private fun handleSearchQuery(query: String) { viewModel.getSearchResult(query) }

}