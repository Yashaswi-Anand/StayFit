package com.yashanand.stayfit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.yashanand.stayfit.R
import com.yashanand.stayfit.adapters.DailyExerciseAdapter
import com.yashanand.stayfit.adapters.Listener
import com.yashanand.stayfit.adapters.VariousExerciseListAdapter
import com.yashanand.stayfit.models.IntroData
import com.yashanand.stayfit.models.timeExercise
import com.yashanand.stayfit.utils.PrefUtil
import kotlinx.android.synthetic.main.activity_various_exercise.*
import kotlinx.android.synthetic.main.start_exercise_layout.*


class VariousExercise : AppCompatActivity(),Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_various_exercise)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if(intent != null){
            if (intent.getStringExtra("Rest") ==  "Rest"){
                listofExercise_RV.visibility= View.GONE
                restView.visibility=View.VISIBLE
                supportActionBar?.hide()
            }
        }

        listofExercise_RV.layoutManager = LinearLayoutManager(this)
        listofExercise_RV.adapter = VariousExerciseListAdapter(this,this, listOf(
            timeExercise("Worm Up", R.raw.run,20),
            timeExercise("Jumping Jack",  R.raw.jumping_jack,20),
            timeExercise("Reverse Crunches",R.raw.reverse_crunches,20),
            timeExercise("Tenor ", R.raw.tenor,20),
            timeExercise("Boat Hold Leg Flutter",  R.raw.boatholdlegflutters,20),
            timeExercise("Russian Twists", R.raw.russian_twists,20),
            timeExercise("Clap Knee Push up",  R.raw.clap_knee_pushup,20),
            timeExercise("Crab Toe Touches ",R.raw.crab_toe_touches,20),
            timeExercise("Cross_Knee Tricep", R.raw.cross_knee_tricep,20),
            timeExercise("Elbow Crunch Right", R.raw.elbowtoknee_crunch_right,20),
            timeExercise("Inchworm",  R.raw.inchworm,20),
            )
        )

    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onItemClicked(name: String, gifImage: Int) {

        val bottomSheetDialog =  BottomSheetDialog(this,R.style.BottomSheetDialogTheme)

        var bottomSheetView = LayoutInflater.from(this).inflate(
            R.layout.start_exercise_layout,
            null
        )
        bottomSheetView.findViewById<TextView>(R.id.exercise_name_open).text = name
        val image = bottomSheetView.findViewById<LottieAnimationView>(R.id.exercise_animation_open)
        Glide.with(this).asGif().load(gifImage).into(image)
        //dialog?.cancel()
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }


}
