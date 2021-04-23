package com.yashanand.stayfit.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.yashanand.stayfit.R
import com.yashanand.stayfit.activities.DashboardActivity

class ChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        view.findViewById<ImageView>(R.id.backToHome).setOnClickListener {
            activity?.startActivity(Intent(activity,DashboardActivity::class.java))
        }
        return  view
    }

}