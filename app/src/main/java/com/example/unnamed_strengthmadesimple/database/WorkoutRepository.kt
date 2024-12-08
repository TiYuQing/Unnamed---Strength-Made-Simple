package com.example.unnamed_strengthmadesimple.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkoutRepository(context: Context) {
    private val sessionDao = DatabaseProvider.getDatabase(context).sessionDao()
    private val exerciseDao = DatabaseProvider.getDatabase(context).exerciseDao()
    private val setsAndRepsDao = DatabaseProvider.getDatabase(context).setsAndRepsDao()
    private val sessionExerciseDao = DatabaseProvider.getDatabase(context).sessionExerciseDao()

    fun addExerciseToSession(sessionId: Int, exerciseId: Int) {
        val crossRef = SessionExerciseCrossRef(sessionId = sessionId, exerciseId = exerciseId)
        sessionExerciseDao.insertExerciseForSession(crossRef)
    }

//    fun getSessionsWithExercises(): List<SessionWithExercises> {
//        val mediatorLiveData = MediatorLiveData<List<SessionWithExercises>>()
//
//        val sessionsLiveData = sessionDao.getAllSessions()
//
//        mediatorLiveData.addSource(sessionsLiveData) { sessions ->
//            val result = mutableListOf<SessionWithExercises>()
//            sessions?.forEach { session ->
//                // Fetch exercises for each session (async background thread)
//                val exercises = exerciseDao.getExercisesForSession(session.id).map { exercise ->
//                    val setsAndReps = setsAndRepsDao.getSetsAndRepsForExercise(exercise.id)
//                    ExerciseWithSetsAndReps(exercise, setsAndReps.toMutableList())
//                }
//                result.add(SessionWithExercises(session, exercises.toMutableList()))
//            }
//            mediatorLiveData.value = result
//        }
//
//        return mediatorLiveData
//    }

    fun getSessionsWithExercises(): List<SessionWithExercises> {
        val sessions = sessionDao.getAllSessions()  // Assuming this returns a List<Session>
        val result = mutableListOf<SessionWithExercises>()

        sessions.forEach { session ->
            val exercises = exerciseDao.getExercisesForSession(session.id).map { exercise ->
                val setsAndReps = setsAndRepsDao.getSetsAndRepsForExercise(exercise.id)
                ExerciseWithSetsAndReps(exercise, setsAndReps.toMutableList())
            }
            result.add(SessionWithExercises(session, exercises.toMutableList()))
        }

        return result
    }

    fun getAllExercises(): List<Exercise> {
        return exerciseDao.getAllExercises()
    }

    fun addSession(session: Session) {
        CoroutineScope(Dispatchers.IO).launch {
            sessionDao.insertSession(session) // Database operation
        }
    }

    fun deleteSession(session: Session) {
        sessionDao.deleteSession(session)
    }

    fun addExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise)
    }


    fun addSet(setsAndReps: SetsAndReps) {
        setsAndRepsDao.insertSetsAndReps(setsAndReps)
    }
}

