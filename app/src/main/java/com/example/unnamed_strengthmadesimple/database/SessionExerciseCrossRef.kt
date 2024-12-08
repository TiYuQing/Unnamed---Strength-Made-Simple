package com.example.unnamed_strengthmadesimple.database

import androidx.room.Entity

@Entity(primaryKeys = ["sessionId", "exerciseId"])
data class SessionExerciseCrossRef(
    val sessionId: Int,
    val exerciseId: Int
)

