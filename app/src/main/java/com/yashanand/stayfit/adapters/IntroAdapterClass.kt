package com.yashanand.stayfit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yashanand.stayfit.R
import com.yashanand.stayfit.models.IntroData

class IntroAdapterClass(private val introSliderData: List<IntroData>) :
    RecyclerView.Adapter<IntroAdapterClass.ViewSliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewSliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.intro_items, parent, false)
        return ViewSliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewSliderViewHolder, position: Int) {
        return holder.bind(introSliderData[position])
    }

    override fun getItemCount(): Int {
        return introSliderData.size
    }

    inner class ViewSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       private val IntroImg: ImageView = itemView.findViewById<ImageView>(R.id.introImg)
       private val IntroText: TextView = itemView.findViewById<TextView>(R.id.introText)

        fun bind(introSlide : IntroData){
            IntroImg.setImageResource(introSlide.IntoImage)
            IntroText.text =introSlide.IntroText
        }
    }

}