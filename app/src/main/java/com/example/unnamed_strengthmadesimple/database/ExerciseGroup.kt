package com.example.unnamed_strengthmadesimple.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

//@Entity(
//    tableName = "exercise_groups",
//    foreignKeys = [
//        ForeignKey(entity = Session::class, parentColumns = ["id"], childColumns = ["sessionId"])
//    ],
//    indices = [Index(value = ["sessionId"])]
//)
//
//data class ExerciseGroup(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val sessionId: Int? = null,
//    val name: String?,
//)