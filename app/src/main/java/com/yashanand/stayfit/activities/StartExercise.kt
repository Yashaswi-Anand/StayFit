package com.yashanand.stayfit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import com.bumptech.glide.Glide
import com.yashanand.stayfit.R
import com.yashanand.stayfit.utils.PrefUtil
import kotlinx.android.synthetic.main.activity_start_exercise.*

class StartExercise : AppCompatActivity() {

    enum class TimerState{
        Stopped, Paused, Running
    }

    private lateinit var timer: CountDownTimer
    private var timerLengthSecond = 0L
    private var timerState = TimerState.Stopped
    private var secondRemaining =0L
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_exercise)

        if (intent != null) {
            val exerciseName = intent.getStringExtra("Name")
            val time = intent.getStringExtra("time")
            val gifImage = intent.getIntExtra("Gif Image",0)
            exName.text = exerciseName
            Glide.with(this).asGif().load(gifImage).into(exGif)
        }

        fab_start.setOnClickListener{v ->
            startTimer()
            timerState =  TimerState.Running
            updateButtons()
        }

        fab_pause.setOnClickListener { v ->
            timer.cancel()
            timerState = TimerState.Paused
            updateButtons()
        }

        fab_stop.setOnClickListener { v ->
            timer.cancel()
            onTimerFinished()
        }
    }

    override fun onResume() {
        super.onResume()

        initTimer()

        // removeAlarm(this)
        //NotificationUtil.hideTimerNotification(this)
    }

    override fun onPause() {
        super.onPause()

        if (timerState == TimerState.Running){
            timer.cancel()
            //val wakeUpTime = setAlarm(this, nowSeconds, secondsRemaining)
            //NotificationUtil.showTimerRunning(this, wakeUpTime)
        }
        else if(timerState == TimerState.Paused){
            //NotificationUtil.showTimerPaused(this)
        }

        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSecond, this)
        PrefUtil.setSecondsRemaining(secondRemaining, this)
        PrefUtil.setTimerState(timerState, this)
    }
    private fun initTimer(){
        timerState = PrefUtil.getTimerState(this)

        //we don't want to change the length of the timer which is already running
        //if the length was changed in settings while it was backgrounded
        if (timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLength()

        secondRemaining = if (timerState == TimerState.Running || timerState == TimerState.Paused)
            PrefUtil.getSecondsRemaining(this)
        else
            timerLengthSecond

        /*  val alarmSetTime = PrefUtil.getAlarmSetTime(this)
          if (alarmSetTime > 0)
              //secondRemaining -= nowSeconds - alarmSetTime*/

        if (secondRemaining <= 0)
            onTimerFinished()
        else if (timerState == TimerState.Running)
            startTimer()

        updateButtons()
        updateCountdownUI()
    }
    private fun onTimerFinished(){
        timerState = TimerState.Stopped

        //set the length of the timer to be the one set in SettingsActivity
        //if the length was changed when the timer was running
        setNewTimerLength()

        progress_countdown.progress = 0

        PrefUtil.setSecondsRemaining(timerLengthSecond, this)
        secondRemaining = timerLengthSecond

        updateButtons()
        updateCountdownUI()
    }

    private fun startTimer(){
        timerState = TimerState.Running

        timer = object : CountDownTimer(secondRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondRemaining = millisUntilFinished / 1000
                updateCountdownUI()
            }
        }.start()
    }

    private fun setNewTimerLength(){
        val lengthInMinutes = PrefUtil.getTimerLength(this)
        timerLengthSecond = (lengthInMinutes * 60L)
        progress_countdown.max = timerLengthSecond.toInt()
    }

    private fun setPreviousTimerLength(){
        timerLengthSecond = PrefUtil.getPreviousTimerLengthSeconds(this)
        progress_countdown.max = timerLengthSecond.toInt()
    }

    private fun updateCountdownUI(){
        val minutesUntilFinished = secondRemaining / 60
        val secondsInMinuteUntilFinished = secondRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        textView_countdown.text = "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
        progress_countdown.progress = (timerLengthSecond - secondRemaining).toInt()
    }

    private fun updateButtons(){
        when (timerState) {
            TimerState.Running ->{
                fab_start.isEnabled = false
                fab_pause.isEnabled = true
                fab_stop.isEnabled = true
            }
            TimerState.Stopped -> {
                fab_start.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = false
            }
            TimerState.Paused -> {
                fab_start.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = true
            }
        }
    }
}