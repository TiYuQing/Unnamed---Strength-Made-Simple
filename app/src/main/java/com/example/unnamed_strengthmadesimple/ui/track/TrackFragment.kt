package com.example.unnamed_strengthmadesimple.ui.track

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unnamed_strengthmadesimple.R
import com.example.unnamed_strengthmadesimple.adapter.SessionAdapter
import com.example.unnamed_strengthmadesimple.database.Exercise
import com.example.unnamed_strengthmadesimple.database.Session
import com.example.unnamed_strengthmadesimple.database.SetsAndReps
import com.example.unnamed_strengthmadesimple.databinding.FragmentTrackBinding
import java.util.Calendar

class TrackFragment : Fragment() {

    private var _binding: FragmentTrackBinding? = null
    private val binding get() = _binding!!

    private lateinit var sessionAdapter: SessionAdapter
    private lateinit var trackViewModel: TrackViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout
        _binding = FragmentTrackBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize ViewModel
        trackViewModel = ViewModelProvider(this).get(TrackViewModel::class.java)

        // Set up the "Add Session" button
        binding.addSessionButton.setOnClickListener {
            showAddSessionDialog()
        }

        // Initialize RecyclerView
        setupRecyclerView()

        // Observe sessions with exercises
        observeData()

        return root
    }

    private fun setupRecyclerView() {
        sessionAdapter = SessionAdapter(
            trackFragment = this,
            mutableListOf(),
            onAddExercise = { sessionWithExercises ->
                // Handle adding an exercise to the session
                showAddExerciseDialog(sessionWithExercises.workoutSession.id)
            },
            onDeleteSession = { sessionWithExercises ->
                // Handle deleting a session
                trackViewModel.deleteSession(sessionWithExercises.workoutSession)
            }
        )

        binding.sessionRecyclerView.apply {
            adapter = sessionAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeData() {
        // Observe the LiveData from ViewModel
        trackViewModel.sessionsWithExercises.observe(viewLifecycleOwner) { sessions ->
            // Update adapter data when sessions are updated
            sessionAdapter.updateData(sessions)
        }
    }

    private fun showAddSessionDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_session, null)
        val sessionNameInput = dialogView.findViewById<EditText>(R.id.sessionNameInput)
        val sessionTimeInput = dialogView.findViewById<EditText>(R.id.sessionTimeInput)

        sessionTimeInput.setOnClickListener {
            showTimePicker { selectedTime ->
                sessionTimeInput.setText(selectedTime)
            }
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Add Session")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = sessionNameInput.text.toString()
                val sessionTime = sessionTimeInput.text.toString()
                val newSession = Session(name = name, startTime = sessionTime, endTime = "")
                trackViewModel.addSession(newSession)

                Toast.makeText(requireContext(), "Session added successfully!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAddExerciseDialog(sessionId: Int) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_exercise, null)
        val exerciseNameInput = dialogView.findViewById<EditText>(R.id.exerciseNameInput)
        val presetSetsInput = dialogView.findViewById<EditText>(R.id.presetSetsInput)
        val presetRepsInput = dialogView.findViewById<EditText>(R.id.presetRepsInput)
        val presetWeightInput = dialogView.findViewById<EditText>(R.id.presetWeightInput)
        val existingExercisesSpinner = dialogView.findViewById<Spinner>(R.id.existingExercisesSpinner)
        val createNewExerciseCheck = dialogView.findViewById<CheckBox>(R.id.createNewExerciseCheck)

        // Create a blank adapter for the spinner
        val spinnerAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            mutableListOf() // Initial empty list
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        existingExercisesSpinner.adapter = spinnerAdapter

        // Observe the LiveData for all exercises
        trackViewModel.allExercises.observe(viewLifecycleOwner) { exercises ->
            // Update the spinner's data when exercises change
            spinnerAdapter.clear()
            spinnerAdapter.addAll(exercises.map { it.name })
            spinnerAdapter.notifyDataSetChanged()
        }

        // Show the dialog
        AlertDialog.Builder(requireContext())
            .setTitle("Add Exercise")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val isNew = createNewExerciseCheck.isChecked
                val exercise = if (isNew) {
                    // Create a new exercise
                    val name = exerciseNameInput.text.toString()
                    val presetSets = presetSetsInput.text.toString().toIntOrNull() ?: 0
                    val presetReps = presetRepsInput.text.toString().toIntOrNull() ?: 0
                    val presetWeight = presetWeightInput.text.toString().toFloatOrNull() ?: 0f
                    val newExercise = Exercise(sessionId = sessionId, name = name, type = null, description = null)
                    trackViewModel.addExercise(newExercise) // Insert into DB
                    newExercise
                } else {
                    // Use an existing exercise
                    trackViewModel.allExercises.value?.get(existingExercisesSpinner.selectedItemPosition)
                }
                exercise?.let { trackViewModel.addExerciseToSession(sessionId, it.id) }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    fun showAddSetDialog(sessionId: Int, exerciseId: Int, currentSetNumber: Int) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_set, null)
        val repsInput = dialogView.findViewById<EditText>(R.id.repsInput)
        val weightInput = dialogView.findViewById<EditText>(R.id.weightInput)

        AlertDialog.Builder(requireContext())
            .setTitle("Add Set")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val reps = repsInput.text.toString().toIntOrNull() ?: 0
                val weight = weightInput.text.toString().toFloatOrNull() ?: 0f
                val newSet = SetsAndReps(
                    exerciseId = exerciseId,
                    sessionId = sessionId,
                    actualSetNumber = currentSetNumber + 1,
                    actualReps = reps,
                    actualWeight = weight
                )
                trackViewModel.addSet(newSet)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showTimePicker(callback: (String) -> Unit) {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val currentMinute = Calendar.getInstance().get(Calendar.MINUTE)

        TimePickerDialog(requireContext(), { _, hour, minute ->
            callback(String.format("%02d:%02d", hour, minute)) // Format to HH:mm
        }, currentHour, currentMinute, true).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
