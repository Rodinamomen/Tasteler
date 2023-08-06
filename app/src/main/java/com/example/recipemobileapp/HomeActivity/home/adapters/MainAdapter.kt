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

class MainAdapter(val data:List<Meal>, private val onItemClick: (pos:Int) -> Unit) : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val row =
            LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false)
        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textViewTitle.text = data[position].strMeal
//        holder.textViewTag.text
        val imgView:ImageView = holder.imageView
        Glide.with(holder.itemView.context)
            .load(data[position].strMealThumb)
            .centerCrop()
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loadingsvg)
                    .error(R.drawable.broken_image))
            .into(imgView)
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val textViewTitle: TextView = row.findViewById(R.id.textView_title)
        val favBtn: ImageButton = row.findViewById(R.id.imageButton2)
        val imageView: ImageView = row.findViewById(R.id.imageView)
    }
}