package com.example.tulip

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import java.util.*


class MainActivity : AppCompatActivity() {

    var timer: Timer? = null
    var timerTask: TimerTask? = null
    lateinit var promptText: TextView
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
                    findViewById<EditText>(R.id.editTextTextPersonName4).apply {
                        if (this.requestFocus()) {

                            val imm = getSystemService(InputMethodManager::class.java)
                            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                        }

                    }

                    val oldText = promptText.text
                    if (promptShow) {
                        promptText.text = oldText?.dropLast(1)
                        promptShow = false
                    } else {
                        promptText.text = "${oldText}_"
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
            text = ""
        }

        findViewById<EditText>(R.id.editTextTextPersonName4).apply {
            setText("")
            doOnTextChanged { text, start, before, count ->
                if (promptShow) {
                    promptText.text = "${text}_"
                } else promptText.text = text
            }
        }
        startTimer()


    }


}