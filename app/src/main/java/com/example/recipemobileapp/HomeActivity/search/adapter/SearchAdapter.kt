package com.example.recipemobileapp.HomeActivity.search.adapter


import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipemobileapp.Authentication.Login.view.LoginFragment
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.R
import com.example.recipemobileapp.ViewModel.SearchViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchAdapter(val data:List<Meal>,val viewModel:SearchViewModel,
                    private val onRecipeClick: (Meal) -> Unit): RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
   lateinit var a:Deferred<Unit>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val row =
            LayoutInflater.from(parent.context).inflate(R.layout.single_item_vertical, parent, false)

        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sharedPreferences = (holder.itemView.context).getSharedPreferences(
            LoginFragment.SHARED_PREFS,
            Context.MODE_PRIVATE)
        holder.textViewTitle.text = data[position].strMeal
        val imgView:ImageView = holder.imageView
        holder.textViewCategory.text= data[position].strCategory
        holder.textViewArea.text=data[position].strArea
        Glide.with(holder.itemView.context)
            .load(data[position].strMealThumb)
            .centerCrop()
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loadingsvg)
                    .error(R.drawable.broken_image))
            .into(imgView)
        var isFavourite:Boolean = false
        holder.favbtn.setImageResource(R.drawable.ic_favorite) // Set default image resource
        a = CoroutineScope(Dispatchers.Main).async {
            isFavourite = viewModel.isFavourite(sharedPreferences.getInt("userId", 0), data[position].idMeal)
            if (isFavourite) {
                holder.favbtn.setImageResource(R.drawable.ic_fav_filled)
            }
        }

        holder.favbtn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                a = CoroutineScope(Dispatchers.Main).async {
                    isFavourite = viewModel.isFavourite(sharedPreferences.getInt("userId", 0), data[position].idMeal)
                }
                a.await()
                if(isFavourite){
                    MaterialAlertDialogBuilder(
                        ContextThemeWrapper(holder.itemView.context, R.style.popupDialog)
                    )
                        .setTitle("Are you sure you want to remove this recipe from favourites?")
                        .setMessage("This action can not be undone!")
                        .setNegativeButton("No") { dialog, which -> }
                        .setPositiveButton("Yes") { dialog, which ->
                            viewModel.deleteMeal(data[position])
                            viewModel.deleteWishlist(Wishlist(sharedPreferences.getInt("userId",0), data[position].idMeal))
                            Toast.makeText(holder.itemView.context, "Deleted from Favourites", Toast.LENGTH_SHORT).show()
                            holder.favbtn.setImageResource(R.drawable.ic_favorite)
                        }
                        .show()
                }else{
                    Toast.makeText(holder.itemView.context,"Added to Favourites", Toast.LENGTH_SHORT).show()
                    viewModel.insertMeal(data[position])
                    viewModel.insertFav(Wishlist(sharedPreferences.getInt("userId",0),data[position].idMeal))
                    holder.favbtn.setImageResource(R.drawable.ic_fav_filled)
                }
            }
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
        val favbtn: ImageButton = row.findViewById(R.id.addtofavs)
        val imageView: ImageView = row.findViewById(R.id.imageView2)
        val textViewCategory: TextView= row.findViewById(R.id.textView_catagory)
        val textViewArea: TextView= row.findViewById(R.id.textView_area)
    }
}