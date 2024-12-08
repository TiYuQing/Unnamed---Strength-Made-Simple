package com.example.unnamed_strengthmadesimple.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.unnamed_strengthmadesimple.R
import com.example.unnamed_strengthmadesimple.database.SetsAndReps

class SetsAndRepsAdapter(
    private val setsAndRepsList: List<SetsAndReps>,
    private val onDeleteSet: (SetsAndReps) -> Unit
) : RecyclerView.Adapter<SetsAndRepsAdapter.SetsAndRepsViewHolder>() {

    inner class SetsAndRepsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repsInput: EditText = itemView.findViewById(R.id.etReps)
        val weightInput: EditText = itemView.findViewById(R.id.etWeight)
        val deleteButton: ImageButton = itemView.findViewById(R.id.btnDeleteSet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetsAndRepsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_setsandreps, parent, false)
        return SetsAndRepsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SetsAndRepsViewHolder, position: Int) {
        val setAndReps = setsAndRepsList[position]
        holder.repsInput.setText(setAndReps.actualReps.toString())
        holder.weightInput.setText(setAndReps.actualWeight.toString() ?: "")
        holder.deleteButton.setOnClickListener { onDeleteSet(setAndReps) }
    }

    override fun getItemCount(): Int = setsAndRepsList.size
}

