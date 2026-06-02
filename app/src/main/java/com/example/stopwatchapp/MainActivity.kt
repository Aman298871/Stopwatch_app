package com.example.stopwatchapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.stopwatchapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var isRunning = false
    private var timeSeconds = 0

    private val handler = Handler(Looper.getMainLooper())

    private val runnable = object : Runnable {
        override fun run() {

            timeSeconds++

            val hours = timeSeconds / 3600
            val minutes = (timeSeconds % 3600) / 60
            val seconds = timeSeconds % 60

            val time = String.format(
                "%02d:%02d:%02d",
                hours,
                minutes,
                seconds
            )

            binding.timer.text = time

            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnstart.setOnClickListener {
            startTimer()
        }

        binding.btnstop.setOnClickListener {
            stopTimer()
        }

        binding.btnrestart.setOnClickListener {
            restartTimer()
        }
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }

    private fun startTimer() {
        if (!isRunning) {

            handler.postDelayed(runnable, 1000)

            isRunning = true

            binding.btnstart.isEnabled = false
            binding.btnstop.isEnabled = true
            binding.btnrestart.isEnabled = true
        }
    }

    private fun stopTimer() {
        if (isRunning) {

            handler.removeCallbacks(runnable)

            isRunning = false

            binding.btnstart.isEnabled = true
            binding.btnstart.text = "Resume"

            binding.btnstop.isEnabled = false
            binding.btnrestart.isEnabled = true
        }
    }

    private fun restartTimer() {

        stopTimer()

        timeSeconds = 0

        binding.timer.text = "00:00:00"

        binding.btnstart.isEnabled = true
        binding.btnstop.isEnabled = false
        binding.btnrestart.isEnabled = false

        binding.btnstart.text = "Start"
    }
}