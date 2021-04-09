package com.yashanand.stayfit.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.yashanand.stayfit.R
import kotlinx.android.synthetic.main.fragment_home.*


@Suppress("DEPRECATION")
class HomeFragment : Fragment() {


    private var progressBar: ProgressBar? = null
    private var i = 0
    private var txtView: TextView? = null
    private val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)

        progressBar = view.findViewById<ProgressBar>(R.id.progressbar) as ProgressBar
        txtView = view.findViewById(R.id.txtView) as TextView
        val btn = view.findViewById(R.id.progressbarbtn) as Button
        var i = progressBar!!.progress
        btn.setOnClickListener {
            Thread(Runnable {
                if(i != 100) {
                    i += 5
                    // Update the progress bar and display the current value
                    handler.post(Runnable {
                        progressBar!!.progress = i
                        txtView!!.text = i.toString() + "/" + progressBar!!.max
                    })
                    try {
                        Thread.sleep(100)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }).start()
        }

        var visibility :Boolean = true
        view.findViewById<TextView>(R.id.bmiCalculator).setOnClickListener {
            if (visibility){
                view.findViewById<ConstraintLayout>(R.id.data_bmi).visibility = View.VISIBLE
                visibility= false
            }else{
                view.findViewById<ConstraintLayout>(R.id.data_bmi).visibility = View.GONE
                visibility= true
            }

        }

        return view
    }

}