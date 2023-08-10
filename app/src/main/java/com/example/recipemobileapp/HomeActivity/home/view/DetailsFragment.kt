package com.example.recipemobileapp.HomeActivity.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.ImageButton
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailsFragment : Fragment(){
    private lateinit var viewModel: MealViewModel
    private lateinit var recipeImageView: ImageView
    private lateinit var recipeNameTextView: TextView
    private lateinit var descriptionExpandableTextView: ExpandableTextView
    private lateinit var descriptionTextView2: TextView
    private lateinit var readmore: TextView
    private lateinit var tutorialyoutubeView: YouTubePlayerView
    lateinit var a:Deferred<Unit>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)


        readmore = view.findViewById(R.id.readmorebtn)
        recipeImageView = view.findViewById(R.id.imageView2)
        recipeNameTextView = view.findViewById(R.id.textView2)
        descriptionExpandableTextView = view.findViewById(R.id.instructionsTextView)
        descriptionTextView2 = view.findViewById(R.id.instructionsTextView2)
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
            val favbtn:ImageButton = view.findViewById(R.id.addtofavs)

            var isFavourite:Boolean = false
            favbtn.setImageResource(R.drawable.ic_favorite) // Set default image resource
            a = CoroutineScope(Dispatchers.Main).async {
                isFavourite = viewModel.isFavourite(sharedPreferences.getInt("userId", 0), recipe.idMeal)
                if (isFavourite) {
                    favbtn.setImageResource(R.drawable.ic_fav_filled)
                }
            }

            favbtn.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    a = CoroutineScope(Dispatchers.Main).async {
                        isFavourite = viewModel.isFavourite(sharedPreferences.getInt("userId", 0), recipe.idMeal)
                    }
                    a.await()
                    if(isFavourite){
                        MaterialAlertDialogBuilder(
                            ContextThemeWrapper(requireContext(), R.style.popupDialog)
                        )
                        .setTitle("Are you sure you want to remove this recipe from favourites?")
                        .setMessage("This action can not be undone!")
                        .setNegativeButton("No") { dialog, which -> }
                        .setPositiveButton("Yes") { dialog, which ->
                            viewModel.deleteMeal(recipe)
                            viewModel.deleteWishlist(Wishlist(sharedPreferences.getInt("userId",0), recipe.idMeal))
                            Toast.makeText(requireContext(), "Deleted from Favourites", Toast.LENGTH_SHORT).show()
                            favbtn.setImageResource(R.drawable.ic_favorite)
                        }
                        .show()
                    }else{
                        Toast.makeText((requireContext()),"Added to Favourites", Toast.LENGTH_SHORT).show()
                        viewModel.insertMeal(recipe)
                        viewModel.insertFav(Wishlist(sharedPreferences.getInt("userId",0),recipe.idMeal))
                        favbtn.setImageResource(R.drawable.ic_fav_filled)
                    }
                }
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
            descriptionExpandableTextView.text = "\n ${recipe.strInstructions}"
            descriptionTextView2.text = "- Area: ${recipe.strArea} \n -Category : ${recipe.strCategory}"


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













