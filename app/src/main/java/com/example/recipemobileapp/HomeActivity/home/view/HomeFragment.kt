package com.example.recipemobileapp.HomeActivity.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemobileapp.Authentication.Login.view.LoginFragment
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepoImpl
import com.example.recipemobileapp.HomeActivity.home.adapters.MainAdapter
import com.example.recipemobileapp.Network.APIClient
import com.example.recipemobileapp.R
import com.example.recipemobileapp.ViewModel.MealViewModel
import com.example.recipemobileapp.ViewModel.MealviewModelFactory

class HomeFragment : Fragment() {
    private lateinit var viewModel: MealViewModel
    private lateinit var recyclerViewRandomMeal: RecyclerView
    private lateinit var recyclerViewAllMeals: RecyclerView
    private lateinit var sharedPreferences:SharedPreferences
    private var savedMealId:Int = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        gettingViewModelReady()
        recyclerViewRandomMeal = view.findViewById(R.id.recyclerView_randomMeal)
        recyclerViewAllMeals = view.findViewById(R.id.recyclerView_home)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewRandomMeal = view.findViewById(R.id.recyclerView_randomMeal)
        recyclerViewAllMeals = view.findViewById(R.id.recyclerView_home)
        sharedPreferences = requireActivity().getSharedPreferences(LoginFragment.SHARED_PREFS, Context.MODE_PRIVATE)


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
                savedMealId = user.userid
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
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.HORIZONTAL, false)
    }
    private fun gettingViewModelReady() {
        val mealFactory = MealviewModelFactory(
            MealRepoImpl(
                APIClient,
                LocalDataSourceImpl(requireContext())
            )
        )
        viewModel = ViewModelProvider(this, mealFactory)[MealViewModel::class.java]
    }
    private fun onRecipeClick(clickedMeal: Meal) {
        val bundle = Bundle()
//        bundle.putInt("recipeId", clickedMeal)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
    }

    override fun onCreateOptionsMenu(menu: Menu , inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu,inflater)
        inflater.inflate(R.menu.option_menu,menu)
        Log.d("Menu", "Menu inflated")
        }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("Menu", "Menu item clicked: ${item.itemId}")
        return when (item.itemId) {
            R.id.aboutFragment -> {
                val navController = findNavController()
                navController.navigate(R.id.aboutFragment)
                true
            }
            R.id.signOut_item -> {
                // Handle sign out action
                Toast.makeText(context, "Sign Out was selected", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        } }




}