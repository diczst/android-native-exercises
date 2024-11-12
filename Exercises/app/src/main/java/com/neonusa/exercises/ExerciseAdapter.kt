package com.neonusa.exercises

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.neonusa.exercises.databinding.ItemExerciseBinding

class ExerciseAdapter(
    val items: List<Exercise>,
    val onClick: (Exercise) -> Unit,

): RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(private val binding: ItemExerciseBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise){
            binding.tvTitle.text = exercise.name
            binding.tvStatus.text = when(exercise.status){
                0 -> "Belum"
                1 -> "Dalam Proses"
                else -> "Selesai"
            }

            when(exercise.status){
                0 -> {binding.tvStatus.setTextColor(ContextCompat.getColor(itemView.context,R.color.danger))}
                1 -> {binding.tvStatus.setTextColor(ContextCompat.getColor(itemView.context,R.color.warning))}
                2 -> {binding.tvStatus.setTextColor(ContextCompat.getColor(itemView.context,R.color.success))}
            }

            binding.root.setOnClickListener {
                onClick(exercise)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}