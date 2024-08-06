package com.example.tulip

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import java.util.*


class MainActivity : AppCompatActivity() {

    val handler: Handler = Handler(Looper.getMainLooper())
    lateinit var timer: Timer
    lateinit var mediaPlayer:MediaPlayer
    var promptVisible = false
    fun initializeTimerTask() :TimerTask{
        return object : TimerTask() {

            override fun run() {
                handler.post {
                    findViewById<EditText>(R.id.input_text).apply {
                        if (this.requestFocus()) {

                            val imm = getSystemService(InputMethodManager::class.java)
                            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                        }

                    }

                    val promptText = findViewById<TextView>(R.id.consolePrompt)
                    val oldText=promptText.text
                    if (promptVisible) {
                        promptText.text = oldText?.dropLast(1)
                        promptVisible = false
                    } else {
                        promptText.text = "${oldText}_"
                        promptVisible = true
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
        val promptText=findViewById<TextView>(R.id.consolePrompt).apply {
            setTextColor(Color.GREEN)
            text = ">"
        }
        mediaPlayer=MediaPlayer.create(this,R.raw.keyboard_key_strike)

        findViewById<EditText>(R.id.input_text).apply {
            setText("")
            doOnTextChanged { text, start, before, count ->
                mediaPlayer.start()
                if (promptVisible) {
                    promptText.text = ">${text}_"
                } else promptText.text = ">$text"
            }
        }
        timer = Timer().apply {  schedule(initializeTimerTask(), 0, 500)}


    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}