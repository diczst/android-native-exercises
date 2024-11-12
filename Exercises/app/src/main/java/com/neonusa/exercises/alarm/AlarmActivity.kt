package com.neonusa.exercises.alarm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.neonusa.exercises.R
import com.neonusa.exercises.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alarmReceiver = AlarmReceiver()

        binding.btnSetAlarm.setOnClickListener {
            alarmReceiver.setRepeatingAlarm(this)
        }

        binding.btnCancelAlarm.setOnClickListener {
        }
    }
}