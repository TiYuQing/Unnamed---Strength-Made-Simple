package com.example.unnamed_strengthmadesimple.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SetsAndRepsDao {
    // Insert a new set and rep
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSetsAndReps(setsAndReps: SetsAndReps)

    // Delete a set and rep
    @Delete
    fun deleteSetsAndReps(setsAndReps: SetsAndReps)

    @Query("SELECT * FROM sets_and_reps WHERE exerciseId = :exerciseId")
    fun getSetsAndRepsForExercise(exerciseId: Int): List<SetsAndReps>

}