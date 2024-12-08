package com.example.unnamed_strengthmadesimple.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unnamed_strengthmadesimple.R
import com.example.unnamed_strengthmadesimple.database.Exercise
import com.example.unnamed_strengthmadesimple.database.ExerciseWithSetsAndReps

class ExerciseAdapter(
    private val sessionId: Int, // Add sessionId to the constructor
    private val exercises: MutableList<ExerciseWithSetsAndReps>,
    private val onAddSet: (sessionId: Int, exerciseWithSetsAndReps: ExerciseWithSetsAndReps) -> Unit,
    private val onDeleteExercise: (ExerciseWithSetsAndReps) -> Unit
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseName: TextView = itemView.findViewById(R.id.tvExerciseName)
        val addSetButton: Button = itemView.findViewById(R.id.btnAddSet)
        val deleteExerciseButton: ImageButton = itemView.findViewById(R.id.btnDeleteExercise)
        val setsAndRepsRecyclerView: RecyclerView = itemView.findViewById(R.id.rvSetsAndReps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.exerciseName.text = exercise.exercise.name

        // Pass sessionId along with exercise to the callback
        holder.addSetButton.setOnClickListener { onAddSet(sessionId, exercise) }
        holder.deleteExerciseButton.setOnClickListener { onDeleteExercise(exercise) }

        // Bind SetsAndRepsAdapter
        val setsAndRepsAdapter = SetsAndRepsAdapter(exercise.setsAndReps) { setToDelete ->
            exercise.setsAndReps.remove(setToDelete)
            notifyItemChanged(position)
        }
        holder.setsAndRepsRecyclerView.adapter = setsAndRepsAdapter
        holder.setsAndRepsRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
    }

    override fun getItemCount(): Int = exercises.size
}


