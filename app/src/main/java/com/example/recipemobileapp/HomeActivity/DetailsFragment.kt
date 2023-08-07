package com.example.recipemobileapp.HomeActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.recipemobileapp.R
import at.blogc.android.views.ExpandableTextView

class DetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_details, container, false)

        val description:ExpandableTextView = view.findViewById(R.id.instructionsTextView)
        val readmore: Button = view.findViewById(R.id.readmore)
        description.setAnimationDuration(750L)
        description.setInterpolator(OvershootInterpolator())
        readmore.setOnClickListener {
            if(description.isExpanded)
            {
                description.collapse()
                readmore.setText("see more")
            }
            else
            {
                description.expand()
                readmore.setText("see less")
            }
        }
        return view
    }


}