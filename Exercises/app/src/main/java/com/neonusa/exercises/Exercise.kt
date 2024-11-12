package com.neonusa.exercises

data class Exercise(
    val id: Int,
    val name: String,
    val status : Int
    // 0: not yet, 1 : progress, 2 : done
)