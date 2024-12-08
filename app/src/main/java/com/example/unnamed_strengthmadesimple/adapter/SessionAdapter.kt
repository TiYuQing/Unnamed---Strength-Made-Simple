package com.example.unnamed_strengthmadesimple.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unnamed_strengthmadesimple.R
import com.example.unnamed_strengthmadesimple.database.Session
import com.example.unnamed_strengthmadesimple.database.SessionWithExercises
import com.example.unnamed_strengthmadesimple.ui.track.TrackFragment

class SessionAdapter(
    private val trackFragment: TrackFragment,
    private val sessions: MutableList<SessionWithExercises>,
    private val onAddExercise: (SessionWithExercises) -> Unit,
    private val onDeleteSession: (SessionWithExercises) -> Unit
) : RecyclerView.Adapter<SessionAdapter.SessionViewHolder>() {

    inner class SessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sessionName: TextView = itemView.findViewById(R.id.sessionNameTextView)
        val addExerciseButton: Button = itemView.findViewById(R.id.addExerciseButton)
        val deleteSessionButton: ImageButton = itemView.findViewById(R.id.btnDeleteSession)
        val exerciseRecyclerView: RecyclerView = itemView.findViewById(R.id.exerciseRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_session, parent, false)
        return SessionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val session = sessions[position]
        holder.sessionName.text = session.workoutSession.name
        holder.addExerciseButton.setOnClickListener {
            onAddExercise(session)
        }
        holder.deleteSessionButton.setOnClickListener { onDeleteSession(session) }

        // Bind ExerciseAdapter
        val exerciseAdapter = ExerciseAdapter(
            sessionId = session.workoutSession.id, // Pass the session ID
            exercises = session.exercises, // Pass the list of exercises
            onAddSet = { sessionId, exercise ->
                // Handle adding a set (pass the sessionId and exercise to your logic)
                // Use trackFragment to call showAddSetDialog
                trackFragment.showAddSetDialog(
                    sessionId, exercise.exercise.id, exercise.setsAndReps.size
                )
            },
            onDeleteExercise = { exerciseToDelete ->
                session.exercises.remove(exerciseToDelete)
                notifyItemChanged(position)
            }
        )
        holder.exerciseRecyclerView.adapter = exerciseAdapter
        holder.exerciseRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
    }

    override fun getItemCount(): Int = sessions.size

    fun updateData(newSessions: List<SessionWithExercises>) {
        sessions.clear() // Clear the old list
        sessions.addAll(newSessions) // Add the new data
        notifyDataSetChanged() // Notify RecyclerView about the changes
    }

}

