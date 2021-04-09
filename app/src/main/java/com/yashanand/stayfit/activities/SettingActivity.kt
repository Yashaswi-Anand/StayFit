package com.yashanand.stayfit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yashanand.stayfit.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
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

           // supportActionBar?.title = intent.getStringExtra("HealthData")
        }
    }

    private fun MI_UNITS() {
        supportActionBar?.title = intent.getStringExtra("MIunit")
        mi_unit_Layout.visibility= View.VISIBLE
    }

    private fun HealthData() {
        supportActionBar?.title = intent.getStringExtra("HealthData")
        healthDataLayout.visibility= View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

