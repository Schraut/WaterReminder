package com.example.waterreminder

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var seekBar : SeekBar
    lateinit var timerDisplay : TextView
    private lateinit var startButton : Button
    var countDownTimer: CountDownTimer? = null
    var counterIsActive = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        seekBar = findViewById(R.id.seekBar)
        seekBar.progress = 0
        timerDisplay = findViewById(R.id.tv_timer_display)
        timerDisplay.text = "00:00"
        startButton = findViewById(R.id.btn_start)
        seekBar.max = 6000 // 100 minutes

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                updateTimer(i)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

//        startButton.setOnClickListener {
//            buttonClicked()
//        }
    }

    fun resetTimer() {
        timerDisplay!!.text = "0:30"
        seekBar!!.progress = 30
        seekBar!!.isEnabled = true
        countDownTimer!!.cancel()
        startButton!!.text = "GO!"
        counterIsActive = false
    }

    fun pauseTimer(view: View) {
        counterIsActive = false
        countDownTimer!!.cancel()
    }

    fun buttonClicked(view: View?) {
        if (counterIsActive) {
            resetTimer()
            startButton.text = "Start"
        } else {
            counterIsActive = true
            seekBar!!.isEnabled = false
            startButton!!.text = "STOP!"
            countDownTimer = object : CountDownTimer((seekBar!!.progress * 1000 + 100).toLong(), 1000) {
                override fun onTick(l: Long) {
                    updateTimer(l.toInt() / 1000)
                }

                override fun onFinish() {

//                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
//                    mplayer.start();
                    //resetTimer()
                }
            }.start()
        }
    }

    fun updateTimer(secondsLeft: Int) {
        val minutes = secondsLeft / 60
        val seconds = secondsLeft - minutes * 60
        var secondString = Integer.toString(seconds)
        if (seconds <= 9) {
            secondString = "0$secondString"
        }
        timerDisplay!!.text = Integer.toString(minutes) + ":" + secondString
    }


}