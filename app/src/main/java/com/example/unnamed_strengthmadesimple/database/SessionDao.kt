package com.example.unnamed_strengthmadesimple.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SessionDao {
    // Insert a new session
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSession(session: Session)

    // Delete a session
    @Delete
    fun deleteSession(session: Session)

    @Query("SELECT * FROM workout_sessions")
    fun getAllSessions(): List<Session>

}