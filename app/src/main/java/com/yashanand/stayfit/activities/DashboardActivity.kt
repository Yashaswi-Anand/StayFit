package com.yashanand.stayfit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.yashanand.stayfit.R
import com.yashanand.stayfit.fragments.ChallengeFragment
import com.yashanand.stayfit.fragments.ChatFragment
import com.yashanand.stayfit.fragments.HomeFragment
import com.yashanand.stayfit.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_dashboard)

        supportActionBar?.hide()
        Glide.with(this).asGif().load(R.drawable.chat_img).into(chatImage)
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout,HomeFragment()).commit()

        BottomMenu.setOnNavigationItemSelectedListener{ item ->
            when(item.itemId){
                R.id.home ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,HomeFragment()).commit()
                    supportActionBar?.hide()
                }
                R.id.challenge ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,ChallengeFragment()).commit()
                    supportActionBar?.hide()
                }
                R.id.profile ->{
                    chatImage.visibility=View.VISIBLE
                    cross_icon.visibility= View.VISIBLE
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,ProfileFragment()).commit()
                    supportActionBar?.hide()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        var checked:Boolean = TRUE
        frameLayout.setOnClickListener {
            if (checked == TRUE){
                BottomMenu.visibility = View.GONE
                checked= FALSE
            }else{
                BottomMenu.visibility = View.VISIBLE
                checked= TRUE
            }
        }
    }

    fun ImageForChat(view: View) {
        Toast.makeText(this,"shoe the chay",Toast.LENGTH_SHORT).show()
        supportActionBar?.title = "Username"
        chatImage.visibility=View.GONE
        cross_icon.visibility= View.GONE
        BottomMenu.visibility = View.GONE
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout,ChatFragment()).commit()

    }

    fun cross_icon(view: View) {
        chatImage.visibility = View.GONE
        cross_icon.visibility= View.GONE
    }
}