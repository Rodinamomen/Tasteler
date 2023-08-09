package com.example.recipemobileapp.HomeActivity.home.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.R

class Top_picked_adapter(val data:List<Meal>, private val onRecipeClick: (Meal) -> Unit,
                         private val onFavClick: (pos:Int) -> Unit) : RecyclerView.Adapter<Top_picked_adapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val row =
            LayoutInflater.from(parent.context).inflate(R.layout.single_item_top_picked, parent, false)

        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
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
        holder.favBtn.setOnClickListener {
            onFavClick(position)
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