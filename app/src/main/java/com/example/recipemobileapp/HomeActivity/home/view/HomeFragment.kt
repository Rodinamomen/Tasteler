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
//import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
import com.example.recipemobileapp.HomeActivity.HomeActivity.Companion.EMAIL_KEY
import com.example.recipemobileapp.HomeActivity.HomeActivity.Companion.PASSWORD_KEY
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepoImpl
import com.example.recipemobileapp.HomeActivity.home.adapters.MainAdapter
import com.example.recipemobileapp.HomeActivity.home.adapters.Top_picked_adapter
import com.example.recipemobileapp.Network.APIClient
import com.example.recipemobileapp.R
import com.example.recipemobileapp.ViewModel.MealViewModel
import com.example.recipemobileapp.ViewModel.MealviewModelFactory
import androidx.navigation.fragment.navArgs


class HomeFragment : Fragment() {
    private lateinit var viewModel: MealViewModel
    private lateinit var recyclerViewRandomMeal: RecyclerView
    private lateinit var recyclerViewAllMeals: RecyclerView
    private lateinit var toolbar: Toolbar
    lateinit var editor: SharedPreferences.Editor
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var savedMealId:String

    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL_KEY = "email_key"
        const val PASSWORD_KEY = "password_key"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        gettingViewModelReady()
        recyclerViewRandomMeal = view.findViewById(R.id.recyclerView_randomMeal)
        recyclerViewAllMeals = view.findViewById(R.id.recyclerView_home)
        setHasOptionsMenu(true)
        gettingViewModelReady()
        sharedPreferences = requireActivity().getSharedPreferences(LoginFragment.SHARED_PREFS,
                                                                        Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email_key","")!!
        viewModel.getUserId(email)
        viewModel.loggedUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                val editor=sharedPreferences.edit()
                editor.putInt("userId",user.userid)
                editor.apply()
            }
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewRandomMeal = view.findViewById(R.id.recyclerView_randomMeal)
        recyclerViewAllMeals = view.findViewById(R.id.recyclerView_home)
        toolbar = view.findViewById(R.id.topbarlayout)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        val processBarMeal:ProgressBar = view.findViewById(R.id.progresBar_allMeals)
        val processBarRandomMeal:ProgressBar = view.findViewById(R.id.progressBar_randomMeal)

        viewModel.getRandomMeal()
        viewModel.getMealsList(('A'..'Z').random())

        viewModel.randomMealList.observe(viewLifecycleOwner){ meals->
            if(meals != null){
                processBarRandomMeal.visibility = View.GONE
                addElementsRandom(meals,recyclerViewRandomMeal)
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
    private fun addElements(data:List<Meal>, recyclerView: RecyclerView){
        recyclerView.adapter = MainAdapter(data, viewModel
        ) { clickedMeal -> onRecipeClick(clickedMeal) }

        recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.HORIZONTAL, false)
    }
    private fun addElementsRandom(data:List<Meal>, recyclerView: RecyclerView){
        recyclerView.adapter = Top_picked_adapter(data,viewModel)
                    { clickedMeal -> onRecipeClick(clickedMeal) }

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
        val action = HomeFragmentDirections.actionHomeFragmentToNewDetailsFragment4(clickedMeal.strMeal,
            clickedMeal.strCategory,clickedMeal.strInstructions,clickedMeal.strYoutube,clickedMeal.strMealThumb,clickedMeal.idMeal,clickedMeal.strArea)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu , inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        super.onCreateOptionsMenu(menu,inflater)
        Log.d("Menu", "Menu inflated")
      }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("Menu", "Menu item clicked: ${item.itemId}")
        return when (item.itemId) {
            R.id.item_about -> {
                val navController = findNavController()
                navController.navigate(R.id.aboutFragment)
                true
            }
            R.id.signOut_item -> {
                // Handle sign out action
                Toast.makeText(context, "Sign Out was selected", Toast.LENGTH_SHORT).show()
                sharedPreferences =  requireActivity().getSharedPreferences(LoginFragment.SHARED_PREFS, Context.MODE_PRIVATE)
                editor=sharedPreferences.edit()
                editor.remove(EMAIL_KEY)
                editor.remove(PASSWORD_KEY)
                editor.commit()
                findNavController().navigate(R.id.aucthenticationActivity)
                requireActivity().finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        } }




}