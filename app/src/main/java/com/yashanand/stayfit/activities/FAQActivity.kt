package com.yashanand.stayfit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ExpandableListView
import android.widget.Toast
import com.yashanand.stayfit.R
import com.yashanand.stayfit.adapters.CustomExpandableListAdapter
import com.yashanand.stayfit.models.ExpandableListData.data

class FAQActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_f_a_q)

        title = "FrequentlyAskedQuestion"
        var expandableListView = findViewById<ExpandableListView>(R.id.expendableList)
        if (expandableListView != null) {
            val listData = data
            var titleList = ArrayList(listData.keys)
            var adapter = CustomExpandableListAdapter(this, titleList as ArrayList<String>, listData)
            expandableListView.setAdapter(adapter)
        /*    expandableListView.setOnGroupExpandListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            expandableListView.setOnGroupCollapseListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            expandableListView.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                Toast.makeText(
                    applicationContext,
                    "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(
                            titleList as
                                    ArrayList<String>
                            )
                            [groupPosition]]!!.get(
                        childPosition
                    ),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }*/
        }

    }
}