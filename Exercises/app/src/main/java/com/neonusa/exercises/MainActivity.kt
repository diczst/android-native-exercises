package com.neonusa.exercises

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neonusa.exercises.alarm.AlarmActivity
import com.neonusa.exercises.databinding.ActivityMainBinding
import com.neonusa.exercises.notification.NotificationActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val items = listOf(
        Exercise(1,"Service",0),
        Exercise(2,"Notification",1),
        Exercise(3,"Alarm",2),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvExercises.addItemDecoration(ExerciseGridDecoration(2, 16, true))
        binding.rvExercises.adapter = ExerciseAdapter(
            items = items,
            onClick = {
                val intent = when(it.id){
                    2 -> Intent(this, NotificationActivity::class.java)
                    3 -> Intent(this, AlarmActivity::class.java)
                    else -> Intent(this, NotificationActivity::class.java)
                }
                startActivity(intent)
            }
        )
    }


}