package com.yashanand.stayfit.activities

import android.app.DatePickerDialog
import android.os.Build.ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.yashanand.stayfit.R
import kotlinx.android.synthetic.main.activity_setting.*
import java.text.SimpleDateFormat
import java.util.*

class SettingActivity : AppCompatActivity() {


    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent != null){
            if (intent.getStringExtra("HealthData") == "Health Data") {
                HealthData()
            }
            if (intent.getStringExtra("MIunit") == "Metric & Imperial Units"){
                MI_UNITS()
            }
        }
    }

    private fun MI_UNITS() {
        supportActionBar?.title = intent.getStringExtra("MIunit")
        mi_unit_Layout.visibility= View.VISIBLE
    }

    private fun HealthData() {
        supportActionBar?.title = intent.getStringExtra("HealthData")
        healthDataLayout.visibility= View.VISIBLE
        gender.setOnClickListener{
            radioGroup.visibility =View.VISIBLE
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                    show_gender.text = radio.text
                    radioGroup.visibility =View.GONE
            }


        }

            // create an OnDateSetListener
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateDateInView()
                }

            // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
            dob!!.setOnClickListener {
                DatePickerDialog(
                    this,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }


    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dobtxt!!.text = sdf.format(cal.time)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

