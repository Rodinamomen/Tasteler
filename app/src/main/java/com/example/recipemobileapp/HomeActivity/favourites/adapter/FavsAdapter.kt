package com.example.recipemobileapp.HomeActivity.favourites.adapter


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
import com.example.recipemobileapp.HomeActivity.favourites.viewModel.FavViewModel
import com.example.recipemobileapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FavsAdapter(val data:MutableList<Meal>, val viewModel: FavViewModel,
                  private val onRecipeClick: (Meal) -> Unit): RecyclerView.Adapter<FavsAdapter.MyViewHolder>() {
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
        Glide.with(holder.itemView.context)
            .load(data[position].strMealThumb)
            .centerCrop()
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loadingsvg)
                    .error(R.drawable.broken_image))
            .into(imgView)
        holder.favBtn.setImageResource(R.drawable.ic_fav_filled)

        holder.favBtn.setOnClickListener {
            MaterialAlertDialogBuilder(
                ContextThemeWrapper(holder.itemView.context, R.style.popupDialog)
            )
                .setTitle("Are you sure you want to remove this recipe from favourites?")
                .setMessage("This action can not be undone!")
                .setNegativeButton("Yes") { dialog, which ->
                    if(data.isNotEmpty()){
                        val clickedMeal = data[position]
                        data.removeAt(position)
                        notifyDataSetChanged()
                        viewModel.deleteMeal(clickedMeal)
                        viewModel.deleteWishlist(Wishlist(sharedPreferences.getInt("userId",0), clickedMeal.idMeal))
                        Toast.makeText(holder.itemView.context, "Deleted from Favs", Toast.LENGTH_SHORT).show()
                    }
                }
                .setPositiveButton("No") { dialog, which ->
                }
                .show()
        }
        holder.itemView.setOnClickListener {
            onRecipeClick(data[position])
        }
        holder.textViewCategory.text= data[position].strCategory
        holder.textViewArea.text=data[position].strArea
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val textViewTitle: TextView = row.findViewById(R.id.textView_title)
        val favBtn: ImageButton = row.findViewById(R.id.imageButton2)
        val imageView: ImageView = row.findViewById(R.id.imageView)
        val textViewCategory: TextView= row.findViewById(R.id.textView_catagory)
        val textViewArea: TextView= row.findViewById(R.id.textView_area)
    }
}