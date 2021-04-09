package com.yashanand.stayfit.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.yashanand.stayfit.R
import com.yashanand.stayfit.activities.VariousExercise

class DailyExerciseAdapter(var context: Context,var listener: MyListener, var items: List<String>):
    RecyclerView.Adapter<DailyExerciseAdapter.dailyExrecise>() {

    private var itemClick:Int=0
        inner class dailyExrecise(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): dailyExrecise {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.dailyexercise_item, parent, false)

            return dailyExrecise(view)

        }

        override fun onBindViewHolder(holder: dailyExrecise, position: Int) {
            var item = items[position]
            holder.itemView.findViewById<TextView>(R.id.daily_item).text = item
            holder.itemView.findViewById<CardView>(R.id.daily_cardview_item).setOnClickListener {
               // Toast.makeText(context, "move to next page.", Toast.LENGTH_SHORT).show()

                if (itemClick < 1){
                    context.startActivity(Intent(context, VariousExercise::class.java))

                }else if(itemClick >=1){
                    listener.onItemClicked()
                   //Toast.makeText(context, "Count is : ${itemClick}", Toast.LENGTH_SHORT).show()
                }
                itemClick++
            }
        }
        override fun getItemCount(): Int {
            return items.size
        }
}

interface  MyListener{
    fun onItemClicked()
}


