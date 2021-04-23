package com.yashanand.stayfit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.yashanand.stayfit.adapters.IntroAdapterClass
import com.yashanand.stayfit.R
import com.yashanand.stayfit.models.IntroData
import kotlinx.android.synthetic.main.activity_intro_viewpager.*


class IntroViewPagerActivity : AppCompatActivity() {

    private val introAdapterClass =
        IntroAdapterClass(
            listOf(
                IntroData("Don't limit your challenges, challenge your limits.", R.drawable.yoga0),
                IntroData("To enjoy the glow of Good Health,you must exercise. ", R.drawable.yoga2),
                IntroData("Exercise should be regarded as tribute to the heart.", R.drawable.yoga3),
                IntroData("Fitness isn't about being better than someone else.It's about being better than you used to be.", R.drawable.yoga4),
                IntroData("Do something today that your future self will thank you for.", R.drawable.yoga1)
            )
        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_viewpager)

        supportActionBar?.hide()
        /*val IntroDataList :ArrayList<IntroData> = ArrayList()
        IntroDataList.add(IntroData("First Page Intro ", R.drawable.yoga0))
        IntroDataList.add(IntroData("Second Page Intro ", R.drawable.yoga2))
        IntroDataList.add(IntroData("Third Page Intro ", R.drawable.yoga3))
        IntroDataList.add(IntroData("fourth Page Intro ", R.drawable.yoga4))
        IntroDataList.add(IntroData("fifth Page Intro ", R.drawable.yoga1))*/

        Intro_viewpager.adapter = introAdapterClass
        setupIndicator()
        setCurrentIndicator(0)
        Intro_viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
                GetStart.visibility =View.GONE
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (Intro_viewpager.currentItem  == introAdapterClass.itemCount-1){
                    GetStart.visibility =View.VISIBLE
                }
            }

        })

        // Log.d("currentitem", introAdapterClass.itemCount.toString()) //5


        next_intro.setOnClickListener {

            if (Intro_viewpager.currentItem + 1 < introAdapterClass.itemCount) {
                Intro_viewpager.currentItem += 1

                //Log.d("currentitem", Intro_viewpager.currentItem.toString())
            } else {
                GetStart.visibility = View.VISIBLE
            }
        }

        GetStart.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        skip_page.setOnClickListener {
            Intro_viewpager.currentItem = introAdapterClass.itemCount
            GetStart.visibility = View.VISIBLE
        }

    }

    fun setupIndicator() {
        val indicator = arrayOfNulls<ImageView>(introAdapterClass.itemCount)
        val layoutParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            WRAP_CONTENT,
            WRAP_CONTENT
        )
        layoutParam.setMargins(8, 0, 8, 0)
        for (i in indicator.indices) {
            indicator[i] = ImageView(applicationContext)
            indicator[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactiive
                    )
                )
                this?.layoutParams = layoutParam

            }
            indicatorContainer.addView(indicator[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactiive
                    )
                )
            }
        }
    }
}