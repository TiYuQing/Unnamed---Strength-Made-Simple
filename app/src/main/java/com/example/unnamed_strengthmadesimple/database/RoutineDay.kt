package com.example.unnamed_strengthmadesimple.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "routine_days",
    foreignKeys = [ForeignKey(entity = Routine::class, parentColumns = ["id"], childColumns = ["routineId"])],
    indices = [Index(value = ["routineId"])]
)
data class RoutineDay(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val routineId: Int,
    val dayOfWeek: String?,
    val date: Long? = null
)
