package com.neonusa.exercises

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.neonusa.exercises.databinding.ActivityMainBinding

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
            items,{
            }
        )
    }


}