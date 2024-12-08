package com.example.unnamed_strengthmadesimple.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout_sessions",
//    foreignKeys = [
//        ForeignKey(entity = RoutineDay::class, parentColumns = ["id"], childColumns = ["routineDayId"]),
//    ],
//    indices = [Index(value = ["routineDayId"])]
)

data class Session(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val routineDayId: Int = 0,
    val name: String?,
    val startTime: String, // Format: HH:mm
    val endTime: String // Format: HH:mm
)

