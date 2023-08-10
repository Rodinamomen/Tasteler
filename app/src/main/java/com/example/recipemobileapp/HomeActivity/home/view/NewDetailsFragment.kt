package com.example.recipemobileapp.HomeActivity.home.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.recipemobileapp.R
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipemobileapp.Authentication.Login.view.LoginFragment
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepoImpl
import com.example.recipemobileapp.Network.APIClient
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

class NewDetailsFragment : Fragment() {
    val navArgs:NewDetailsFragmentArgs by navArgs()
    lateinit var viewModel:MealViewModel
    lateinit var a:Deferred<Unit>
    var isFavourite:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_details, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gettingViewModelReady()
        val textViewTitle:TextView = view.findViewById(R.id.textView_titleDetails)
        val textViewInstructions:TextView = view.findViewById(R.id.textView_instructions)
        val textViewCategory:TextView = view.findViewById(R.id.textView_catagory)
        val imageView: ImageView = view.findViewById(R.id.imageView3)
        val youTubePlayer: YouTubePlayerView = view.findViewById(R.id.youtube_player_view_details)
        val favBtn:ImageButton = view.findViewById(R.id.btn_fav)
        val sharedPreferences = (requireContext()).getSharedPreferences(
            LoginFragment.SHARED_PREFS,
            Context.MODE_PRIVATE)
        textViewTitle.text = navArgs.mealTitle
        textViewCategory.text = navArgs.mealCategory
        textViewInstructions.text = navArgs.mealInstructions

        favBtn.setImageResource(R.drawable.ic_favorite) // Set default image resource
        a = lifecycleScope.async {
            isFavourite = viewModel.isFavourite(sharedPreferences.getInt("userId", 0), navArgs.mealId)
            if (isFavourite) {
                favBtn.setImageResource(R.drawable.ic_fav_filled)
            }
        }


        favBtn.setOnClickListener {
            lifecycleScope.launch {
                a.await()
                if(isFavourite){
                    MaterialAlertDialogBuilder(
                        ContextThemeWrapper(requireContext(), R.style.popupDialog)
                    )
                        .setTitle("Are you sure you want to remove this recipe from favourites?")
                        .setMessage("This action can not be undone!")
                        .setNegativeButton("Yes") { dialog, which ->
                            viewModel.deleteMeal(Meal(navArgs.mealId,navArgs.mealArea,navArgs.mealCategory,
                                navArgs.mealInstructions,navArgs.mealTitle,navArgs.mealImage,null,navArgs.mealYoutubeVideo))
                            viewModel.deleteWishlist(Wishlist(sharedPreferences.getInt("userId",0), navArgs.mealId))
                            Toast.makeText(requireContext(), "Deleted from Favs", Toast.LENGTH_SHORT).show()
                        }
                        .setPositiveButton("No") { dialog, which ->
                        }
                        .show()
                    favBtn.setImageResource(R.drawable.ic_favorite)

                }else{
                    Toast.makeText((requireContext()),"Added to Favs", Toast.LENGTH_SHORT).show()
                    viewModel.insertMeal(Meal(navArgs.mealId,navArgs.mealArea,navArgs.mealCategory,
                        navArgs.mealInstructions,navArgs.mealTitle,navArgs.mealImage,null,navArgs.mealYoutubeVideo))
                    viewModel.insertFav(Wishlist(sharedPreferences.getInt("userId",0),navArgs.mealId))
                    favBtn.setImageResource(R.drawable.ic_fav_filled)
                }
            }
        }
        Glide.with(requireContext())
            .load(navArgs.mealImage)
            .centerCrop()
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loadingsvg)
                    .error(R.drawable.broken_image))
            .into(imageView)

        youTubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                val videoId = navArgs.mealYoutubeVideo.split("watch?v=")
                Log.d("vid",navArgs.mealYoutubeVideo)
                Log.d("vid",videoId[1])
                youTubePlayer.loadVideo(videoId[1], 0F)
                youTubePlayer.pause()

            }
        })
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