package com.example.unnamed_strengthmadesimple.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sets_and_reps",
    foreignKeys = [
        ForeignKey(entity = Exercise::class, parentColumns = ["id"], childColumns = ["exerciseId"]),
        ForeignKey(entity = Session::class, parentColumns = ["id"], childColumns = ["sessionId"])
    ],
    indices = [Index(value = ["exerciseId"]), Index(value = ["sessionId"])]
)
data class SetsAndReps(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val exerciseId: Int,
    val sessionId: Int,
    val actualSetNumber: Int?,
    val actualReps: Int?,
    val actualWeight: Float?,
)

