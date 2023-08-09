package com.example.recipemobileapp.HomeActivity.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import at.blogc.android.views.ExpandableTextView
import com.bumptech.glide.Glide
import com.example.recipemobileapp.Authentication.Login.view.LoginFragment
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepoImpl
import com.example.recipemobileapp.Network.APIClient
import com.example.recipemobileapp.R
import com.example.recipemobileapp.ViewModel.MealViewModel
import com.example.recipemobileapp.ViewModel.MealviewModelFactory
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class DetailsFragment : Fragment(){
    private lateinit var viewModel: MealViewModel
    private lateinit var recipeImageView: ImageView
    private lateinit var recipeNameTextView: TextView
    private lateinit var descriptionExpandableTextView: ExpandableTextView
    private lateinit var descriptionExpandableTextView2: ExpandableTextView
    private lateinit var readmore: TextView
    private lateinit var tutorialyoutubeView: YouTubePlayerView





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)


        readmore = view.findViewById(R.id.readmorebtn)
        recipeImageView = view.findViewById(R.id.imageView2)
        recipeNameTextView = view.findViewById(R.id.textView2)
        descriptionExpandableTextView = view.findViewById(R.id.instructionsTextView)
        descriptionExpandableTextView2 = view.findViewById(R.id.instructionsTextView2)
        tutorialyoutubeView = view.findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(tutorialyoutubeView)

        descriptionExpandableTextView.setAnimationDuration(750L)
        descriptionExpandableTextView.setInterpolator(OvershootInterpolator())

        readmore.setOnClickListener {
            if (descriptionExpandableTextView.isExpanded) {
                descriptionExpandableTextView.collapse()
                readmore.text = "read more"
            } else {
                descriptionExpandableTextView.expand()
                readmore.text = "read less"
            }
        }
        descriptionExpandableTextView2.setAnimationDuration(750L)
        descriptionExpandableTextView2.setInterpolator(OvershootInterpolator())

        descriptionExpandableTextView2.setOnClickListener {
            if (descriptionExpandableTextView2.isExpanded) {
                descriptionExpandableTextView2.collapse()
            } else {
                descriptionExpandableTextView2.expand()
            }
        }
        return view
    }


    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gettingViewModelReady()
        val sharedPreferences = requireActivity().
        getSharedPreferences(LoginFragment.SHARED_PREFS, Context.MODE_PRIVATE)

        val recipe = arguments?.parcelable<Meal>("recipe")
        if (recipe != null) {
            val favbtn:Button = view.findViewById(R.id.addtofavs)
            favbtn.setOnClickListener{
                val clickedMeal = recipe
                Toast.makeText(requireContext(),"Added to Favs", Toast.LENGTH_SHORT).show()
                viewModel.insertMeal(clickedMeal)
                viewModel.insertFav(Wishlist(sharedPreferences.getInt("userId",0),clickedMeal.idMeal))
            }

            tutorialyoutubeView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    val videoId = recipe.strYoutube.substring(recipe.strYoutube.length-11,recipe.strYoutube.length)
                    Log.d("vid",recipe.strYoutube)
                    Log.d("vid",videoId)
                    youTubePlayer.loadVideo(videoId, 0F)
                    youTubePlayer.pause()

                }
            })

            Glide.with(requireContext())
                .load(recipe.strMealThumb)
                .into(recipeImageView)

            recipeNameTextView.text = recipe.strMeal
            descriptionExpandableTextView.text = "Instructions : \n ${recipe.strInstructions}"
            descriptionExpandableTextView2.text = "General Information : \n - Area: ${recipe.strArea} \n -Category : ${recipe.strCategory}\n -Tags : ${recipe.strTags} \n"


        } else {
            Toast.makeText(requireContext(), "Recipe not found", Toast.LENGTH_SHORT).show()
        }

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













}

    inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }













