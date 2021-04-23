package com.yashanand.stayfit.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.yashanand.stayfit.R
import com.yashanand.stayfit.activities.StartExercise
import com.yashanand.stayfit.activities.VariousExercise
import com.yashanand.stayfit.models.IntroData
import com.yashanand.stayfit.models.timeExercise

class VariousExerciseListAdapter(
    var context: Context,
    var listener: Listener,
    private val list: List<timeExercise>
) :
    RecyclerView.Adapter<VariousExerciseListAdapter.various_exrecise_list>() {

    inner class various_exrecise_list(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): various_exrecise_list {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.exercise_list_item, parent, false)

        return various_exrecise_list(view)

    }

    override fun onBindViewHolder(holder: various_exrecise_list, position: Int) {
        val item = list[position]
        holder.itemView.findViewById<TextView>(R.id.exercise_name).text = item.exerciseName
        holder.itemView.findViewById<TextView>(R.id.exercise_time).text = item.time.toString()
        Glide.with(context).asGif().load(item.exerciseImage)
            .into(holder.itemView.findViewById(R.id.gif_image))
        holder.itemView.findViewById<LottieAnimationView>(R.id.gif_image).setOnClickListener {
            listener.onItemClicked(item.exerciseName, item.exerciseImage)
        }
        holder.itemView.findViewById<LinearLayout>(R.id.ll_openAnimation).setOnClickListener {
            context.startActivity(
                Intent(context, StartExercise::class.java)
                    .putExtra("Name", item.exerciseName)
                    .putExtra("time", item.time)
                    .putExtra("Gif Image", item.exerciseImage)
            )
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}

interface Listener {
    fun onItemClicked(name: String, gifImage: Int)
}