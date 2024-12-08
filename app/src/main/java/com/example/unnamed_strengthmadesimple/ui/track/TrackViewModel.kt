package com.example.unnamed_strengthmadesimple.ui.track

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unnamed_strengthmadesimple.database.Exercise
import com.example.unnamed_strengthmadesimple.database.Session
import com.example.unnamed_strengthmadesimple.database.SessionWithExercises
import com.example.unnamed_strengthmadesimple.database.SetsAndReps
import com.example.unnamed_strengthmadesimple.database.WorkoutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrackViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WorkoutRepository(application)

    // LiveData for sessions with exercises
    private val _sessionsWithExercises = MutableLiveData<List<SessionWithExercises>>()
    val sessionsWithExercises: LiveData<List<SessionWithExercises>> = _sessionsWithExercises

    // LiveData for all exercises
    val allExercises: MutableLiveData<List<Exercise>> = MutableLiveData()

    init {
        loadSessionsWithExercises()
        loadAllExercises()
    }

    private fun loadSessionsWithExercises() {
        viewModelScope.launch(Dispatchers.IO) {
            // Fetch the sessions on a background thread
            val sessionList = repository.getSessionsWithExercises()

            // Post the result to LiveData on the main thread
            withContext(Dispatchers.Main) {
                _sessionsWithExercises.value = sessionList
            }
        }
    }

    private fun loadAllExercises() {
        viewModelScope.launch(Dispatchers.IO) {  // Ensure background thread usage for DB fetch
            val exercises = repository.getAllExercises()  // Fetch all exercises
            withContext(Dispatchers.Main) {  // Switch back to Main thread for UI updates
                allExercises.postValue(exercises)  // Update LiveData on Main thread
            }
        }
    }

    fun addSession(session: Session) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSession(session)
            // Trigger LiveData to fetch updated data
            val updatedSessions = repository.getSessionsWithExercises()
            withContext(Dispatchers.Main) {
                _sessionsWithExercises.value = updatedSessions
            }
        }
    }

    fun deleteSession(session: Session) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSession(session)
            loadSessionsWithExercises()  // Refresh data after deletion
        }
    }

    fun addExerciseToSession(sessionId: Int, exerciseId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addExerciseToSession(sessionId, exerciseId)
            loadSessionsWithExercises()  // Optional: Refresh data if needed
        }
    }

    // Change to return LiveData instead of just the list
    fun getAllExercises(): LiveData<List<Exercise>> {
        val liveData = MutableLiveData<List<Exercise>>()
        viewModelScope.launch(Dispatchers.IO) {
            val exercises = repository.getAllExercises()
            withContext(Dispatchers.Main) {
                liveData.value = exercises  // Set the value of the LiveData on the main thread
            }
        }
        return liveData
    }

    fun addExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addExercise(exercise)
            loadAllExercises()  // Refresh all exercises list
        }
    }

    fun addSet(setsAndReps: SetsAndReps) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSet(setsAndReps)
            loadSessionsWithExercises()  // Refresh data
        }
    }
}



