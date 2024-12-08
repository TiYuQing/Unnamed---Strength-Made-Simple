package com.example.unnamed_strengthmadesimple.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface SessionExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExerciseForSession(crossRef: SessionExerciseCrossRef)


}
