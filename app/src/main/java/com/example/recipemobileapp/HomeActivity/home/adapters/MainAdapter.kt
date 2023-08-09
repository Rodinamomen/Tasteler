package com.example.recipemobileapp.HomeActivity.home.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipemobileapp.Authentication.Login.view.LoginFragment
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepoImpl
import com.example.recipemobileapp.Network.APIClient
import com.example.recipemobileapp.R
import com.example.recipemobileapp.ViewModel.MealViewModel
import com.example.recipemobileapp.ViewModel.MealviewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainAdapter(val data:List<Meal>,
                  val viewModel: MealViewModel,
                  private val onRecipeClick: (Meal) -> Unit) :RecyclerView.Adapter<MainAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val row =
            LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false)

        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sharedPreferences = (holder.itemView.context).getSharedPreferences(
            LoginFragment.SHARED_PREFS,
            Context.MODE_PRIVATE)
        holder.textViewTitle.text = data[position].strMeal
        val imgView:ImageView = holder.imageView
        Glide.with(holder.itemView.context)
            .load(data[position].strMealThumb)
            .centerCrop()
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loadingsvg)
                    .error(R.drawable.broken_image))
            .into(imgView)
        holder.favBtn.setImageResource(R.drawable.ic_favorite) // Set default image resource
        CoroutineScope(Dispatchers.Main).launch {
            val isFavorite = viewModel.isFavourite(sharedPreferences.getInt("userId", 0), data[position].idMeal)
            if (isFavorite) {
                holder.favBtn.setImageResource(R.drawable.ic_fav_filled)
            }
        }

        holder.favBtn.setOnClickListener {
            val clickedMeal = data[position]
            Toast.makeText((holder.itemView.context),"Added to Favs", Toast.LENGTH_SHORT).show()
            viewModel.insertMeal(clickedMeal)
            viewModel.insertFav(Wishlist(sharedPreferences.getInt("userId",0),clickedMeal.idMeal))
            holder.favBtn.setImageResource(R.drawable.ic_fav_filled)

        }
        val meal = data[position]

        holder.itemView.setOnClickListener {
            onRecipeClick(meal)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val textViewTitle: TextView = row.findViewById(R.id.textView2)
        val favBtn: ImageButton = row.findViewById(R.id.addtofavs)
        val imageView: ImageView = row.findViewById(R.id.imageView2)
    }
}