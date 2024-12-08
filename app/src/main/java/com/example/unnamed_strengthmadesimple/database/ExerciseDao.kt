package com.example.unnamed_strengthmadesimple.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExerciseDao {
    // Insert a new exercise
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExercise(exercise: Exercise)

    // Delete an exercise
    @Delete
    fun deleteExercise(exercise: Exercise)

    @Query("SELECT * FROM exercises WHERE sessionId = :sessionId")
    fun getExercisesForSession(sessionId: Int): List<Exercise>

    @Query("SELECT * FROM exercises")
    fun getAllExercises(): List<Exercise>
}