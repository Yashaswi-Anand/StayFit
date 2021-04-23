package com.yashanand.stayfit.fragments

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.yashanand.stayfit.R
import com.yashanand.stayfit.activities.VariousExercise
import com.yashanand.stayfit.utils.PrefUtil
import java.util.*


@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    var height: String? = null
    var weight: String? = null
    private var i = 0
    private var txtView: TextView? = null
    private val sharedPrefFile = "sharedpreference"
    private lateinit var sharedPreferences: SharedPreferences
    private val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        sharedPreferences= activity?.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)!!

        txtView = view.findViewById(R.id.txtView) as TextView
        val btn = view.findViewById(R.id.progressbarbtn) as Button
        btn.setOnClickListener {
            startActivity(Intent(activity, VariousExercise::class.java))
        }

        //bottom navigation visibility ----------------------------------------------------------
        var visibility: Boolean = true
        view.findViewById<TextView>(R.id.bmiCalculator).setOnClickListener {
            if (visibility) {
                view.findViewById<ConstraintLayout>(R.id.data_bmi).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.up_down_arrow).setImageResource(R.drawable.ic_arrow_up)
                visibility = false
            } else {
                view.findViewById<ConstraintLayout>(R.id.data_bmi).visibility = View.GONE
                view.findViewById<ImageView>(R.id.up_down_arrow).setImageResource(R.drawable.ic_arrow_down)
                visibility = true
            }
        }
        //bottom navigation visibility ----------------------------------------------------------


        //spinner----------------------------------------------------------------------------------
        val weights = arrayOf("kg", "lbs")
        val weight_spinner = view.findViewById<Spinner>(R.id.weight_spinner)
        //val result =view.findViewById<TextView>(R.id.result)
        weight_spinner.adapter =ArrayAdapter(activity!!,android.R.layout.simple_list_item_1,weights)
        weight_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                weight=weights[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        val heights = arrayOf("cm", "inch")
        val height_spinner = view.findViewById<Spinner>(R.id.height_spinner)
        val result =view.findViewById<TextView>(R.id.result)
        height_spinner.adapter =ArrayAdapter(activity!!,android.R.layout.simple_list_item_1,heights)
        height_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                height =heights[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        view.findViewById<Button>(R.id.result_calculator).setOnClickListener {
            resultCalculate(view,weight,height)
        }

        //spinner---------------------------------------------------------------------------------------------


        view.findViewById<Button>(R.id.drinkingWater).setOnClickListener {
            Drink_daily()
        }

        view.findViewById<CardView>(R.id.quickFit).setOnClickListener {
            startActivity(Intent(activity, VariousExercise::class.java))
        }
        return view
    }

    private fun resultCalculate(view: View, weight: String?, height: String?) {

        val h= view.findViewById<EditText>(R.id.heightNo).text.toString()
        val w= view.findViewById<EditText>(R.id.weightNo).text.toString()
        var ans: String? = null

        if (weight == "kg" && height == "cm"){
            ans= ((w.toFloat()/((h.toFloat()/100)*(h.toFloat()/100))).toInt()).toString() +" kg/m2"
        }
        if (weight == "lbs" && height == "inch"){
           ans = (703*w.toInt()/(h.toInt()*h.toInt())).toString()  + " lds/in2"
        }
        view.findViewById<TextView>(R.id.result).text = "BMI: $ans"

    }

    private fun Drink_daily(){

        val builder = AlertDialog.Builder(activity)
        //set title for alert dialog
        //builder.setTitle("title")
        val customLayout: View = layoutInflater
            .inflate(
                R.layout.water_tracking_layout,
                null
            )
        customLayout.findViewById<Button>(R.id.water_tracker_counter_btn).setOnClickListener {
            Toast.makeText(activity,"great...",Toast.LENGTH_SHORT).show()


            val progressBar = customLayout.findViewById<ProgressBar>(R.id.water_tracker_counter)
            var i = progressBar.progress
            val waterLevelTxt = customLayout.findViewById<TextView>(R.id.wl)
            var waterlevel:Int = 0


                //save data in share preference
            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putInt("liter",i)
            editor.apply()
            editor.commit()

            Thread {
                if (i != 100) {
                    i+=12

                    // Update the progress bar and display the current value
                    handler.post(Runnable {
                        progressBar.progress = i
                        waterlevel +=1
                        txtView!!.text = i.toString() + "/" + progressBar!!.max
                        waterLevelTxt.text ="Water level:  "+(waterlevel).toString() + " liter"


                    })
                    try {
                        Thread.sleep(100)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }.start()

            //view data from share preference
            val sharedIdValue = sharedPreferences.getInt("liter",0)
            //customLayout.findViewById<TextView>(R.id.progressStatuss).text = sharedIdValue.toString()
        }
        builder.setView(customLayout)
        val alertDialog: AlertDialog = builder.create()
        /*builder.setPositiveButton("Yes"){ dialogInterface, which ->
            Toast.makeText(activity,"clicked yes", Toast.LENGTH_LONG).show()
            alertDialog.dismiss()
        }*/
        // Set other dialog properties
        alertDialog.setCancelable(true)
        alertDialog.show()

    }

}


