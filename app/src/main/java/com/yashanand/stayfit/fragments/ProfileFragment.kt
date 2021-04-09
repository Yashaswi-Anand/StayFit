package com.yashanand.stayfit.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.yashanand.stayfit.R
import com.yashanand.stayfit.activities.FAQActivity
import com.yashanand.stayfit.activities.LoginActivity
import com.yashanand.stayfit.activities.SettingActivity
import com.yashanand.stayfit.activities.SplashScreen
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {


    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val views = inflater.inflate(R.layout.fragment_profile, container, false)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        // for person name
        views.findViewById<TextView>(R.id.personName).text = currentUser?.displayName
        // for person email
        views.findViewById<TextView>(R.id.personEmail).text = currentUser?.email
        // for person profile image
        activity?.let { Glide.with(it).load(currentUser?.photoUrl)
            .error(R.drawable.ic_launcher_foreground).into(views.findViewById(R.id.personImg)) }

        views.findViewById<TextView>(R.id.logout).setOnClickListener {
           mAuth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
        }
        views.findViewById<TextView>(R.id.rateUs).setOnClickListener {
            ratingBar()
        }
        views.findViewById<TextView>(R.id.shareWithFriends).setOnClickListener {
            share()
        }
        views.findViewById<TextView>(R.id.sendFeedback).setOnClickListener {
            sendFeedback()
        }
        views.findViewById<TextView>(R.id.faq).setOnClickListener {
            activity?.startActivity(Intent(activity,FAQActivity::class.java ))
        }

        views.findViewById<TextView>(R.id.healthData).setOnClickListener {
            activity?.startActivity(Intent(activity,SettingActivity::class.java ).putExtra("HealthData","Health Data"))
        }
        views.findViewById<TextView>(R.id.MIunit).setOnClickListener {
            activity?.startActivity(Intent(activity, SettingActivity::class.java)
                .putExtra("MIunit","Metric & Imperial Units"))
        }



        return views
    }

    private fun ratingBar(){
        val builder = AlertDialog.Builder(activity)
        //set title for alert dialog
        //builder.setTitle("title")
        val customLayout: View = layoutInflater
            .inflate(
                R.layout.custom_rating_layout,
                null
            )
        builder.setView(customLayout)
       /* //set message for alert dialog
        //builder.setMessage("Do you want to logout...")
        //builder.setIcon(android.R.drawable.ic_dialog_alert)
        //performing positive action
        builder.setPositiveButton("Yes"){ dialogInterface, which ->
            //activity?.let { ActivityCompat.finishAffinity(it) }
            //Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
        }
        //performing cancel action
        builder.setNeutralButton("Cancel"){ dialogInterface, which ->
            // Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
        }
        //performing negative action
        builder.setNegativeButton("No"){ dialogInterface, which ->
            // Toast.makeText(applicationContext,"clicked No",Toast.LENGTH_LONG).show()
        }*/
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    private fun share(){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        //intent.putExtra(Intent.EXTRA_TEXT, currentImageUrl)
        val chooser = Intent.createChooser(intent, "Share this meme using...")
        startActivity(chooser)
    }
    private fun sendFeedback(){
        val email = Intent(Intent.ACTION_SEND)
        email.type = "text/plain"
        email.putExtra(Intent.EXTRA_EMAIL, "yash.kwr17@gmail.com")
        email.putExtra(Intent.EXTRA_SUBJECT, "Dummy message")
        email.putExtra(Intent.EXTRA_TEXT, "StayFit")
        startActivity(email)

    }

}