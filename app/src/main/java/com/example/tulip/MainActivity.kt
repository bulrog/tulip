package com.example.tulip

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    var timer: Timer? = null
    var timerTask: TimerTask? = null
    var promptText: TextView? = null
    var promptShow = true
    // TODO: handler is deprecated so to review:
    val handler: Handler = Handler()

    fun startTimer() {
        //set a new Timer
        timer = Timer()

        //initialize the TimerTask's job
        initializeTimerTask()

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer?.schedule(timerTask, 0, 500) //
    }

    // TODO: add when app not in focus and also when resume to restart the timer, See example: https://examples.javacodegeeks.com/android/core/activity/android-timertask-example/
    fun stoptimertask() {
        //stop the timer, if it's not already null
        timer?.cancel()
        timer = null
    }

    fun initializeTimerTask() {
        timerTask = object : TimerTask() {

            override fun run() {
                handler.post {
                    val oldText = promptText?.text
                    if (promptShow) {

                        promptText?.text = "${oldText}_"
                        promptShow = false
                    } else {
                        promptText?.text = oldText?.dropLast(1)
                        promptShow = true
                    }
                }
            }


        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.decorView.setBackgroundColor(Color.BLACK)
        setContentView(R.layout.console)
        promptText = findViewById<TextView>(R.id.consolePrompt).apply {
            setTextColor(Color.GREEN)
            text = " this is some text"
        }
        startTimer()


    }


}