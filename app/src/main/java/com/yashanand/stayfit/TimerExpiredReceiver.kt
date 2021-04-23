package com.yashanand.stayfit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yashanand.stayfit.activities.StartExercise
import com.yashanand.stayfit.fragments.HomeFragment
import com.yashanand.stayfit.utils.PrefUtil

class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {


        PrefUtil.setTimerState(StartExercise.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}