package com.example.recipemobileapp.HomeActivity.home.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.blogc.android.views.ExpandableTextView
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepoImpl
import com.example.recipemobileapp.HomeActivity.home.adapters.MainAdapter
import com.example.recipemobileapp.Network.APIClient
import com.example.recipemobileapp.R
import com.example.recipemobileapp.ViewModel.MealViewModel
import com.example.recipemobileapp.ViewModel.MealviewModelFactory


class DetailsFragment : Fragment() {
    private lateinit var viewModel: MealViewModel
    private lateinit var recipeImageView: ImageView
    private lateinit var recipeNameTextView: TextView
    private lateinit var recipeCategoryTextView: TextView
    private lateinit var descriptionExpandableTextView: ExpandableTextView
    private lateinit var tutorialWebView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        gettingViewModelReady()
        recipeImageView = view.findViewById(R.id.imageView2)
        recipeNameTextView = view.findViewById(R.id.textView2)
        descriptionExpandableTextView = view.findViewById(R.id.instructionsTextView)
        tutorialWebView = view.findViewById(R.id.webview)

        descriptionExpandableTextView.setAnimationDuration(750L)
        descriptionExpandableTextView.setInterpolator(OvershootInterpolator())

        descriptionExpandableTextView.setOnClickListener {
            if (descriptionExpandableTextView.isExpanded) {
                descriptionExpandableTextView.collapse()
            } else {
                descriptionExpandableTextView.expand()
            }
        }



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipeId = arguments?.getInt("recipeId", -1) ?: -1
        if (recipeId == -1) {
            Toast.makeText(requireContext(), "Invalid recipe ID", Toast.LENGTH_SHORT).show()
            return
        }

//        viewModel.getMealbyID(recipeId).observe(viewLifecycleOwner) { recipe ->
//            if (recipe != null) {
//                val favbtn:Button = view.findViewById(R.id.addtofavs)
//                favbtn.setOnClickListener{
////                    viewModel.insertFav(Wishlist(1,recipeId))
//                    Toast.makeText(requireContext(),"Added to Favs", Toast.LENGTH_SHORT).show()
//                }
//                //recipeImageView.setImageResource() // Replace with actual image
//                recipeNameTextView.text = recipe.strMeal
//                descriptionExpandableTextView.text = recipe.strInstructions
//                // var string: String = recipe.strYoutube
//                // tutorialWebView.loadUrl(string) // Replace with actual tutorial URL
//
//            } else {
//                Toast.makeText(requireContext(), "Recipe not found", Toast.LENGTH_SHORT).show()
//            }
//        }
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

    private fun addElements(data:List<Meal>, recyclerView: RecyclerView){
        /**Make it in image view instead**/
        recyclerView.adapter = MainAdapter(data,{clickedMeal -> onRecipeClick(clickedMeal)}){ position ->
            val clickedMeal = data[position]
            Toast.makeText(requireContext(),"Added to Favs", Toast.LENGTH_SHORT).show()
            Log.d("TAG", "addElements: ${data[position]}")
//            viewModel.insertFav(Wishlist(1, clickedMeal.idMeal))
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.HORIZONTAL, false)
    }
    private fun onRecipeClick(clickedMeal: Meal) {
        val bundle = Bundle()
//        bundle.putInt("recipeId", clickedMeal._mealid)
        findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, bundle)
    }

}