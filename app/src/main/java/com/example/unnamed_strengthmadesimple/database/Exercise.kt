package com.example.unnamed_strengthmadesimple.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercises",
    foreignKeys = [
//        ForeignKey(entity = ExerciseGroup::class, parentColumns = ["id"], childColumns = ["groupId"]),
        ForeignKey(entity = Session::class, parentColumns = ["id"], childColumns = ["sessionId"], onDelete = ForeignKey.CASCADE)
                  ],
    indices = [Index(value = ["sessionId"])]
)
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val groupId: Int,
    val sessionId: Int,
    val name: String,
    val presetSets: Int? = null,
    val presetReps: Int? = null,
    val presetWeight: Float? = null,
    val type: String? = null,
    val description: String? = null
)
