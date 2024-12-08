package com.example.unnamed_strengthmadesimple.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "evaluation_table")
data class Evaluation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val benchPr: Float,
    val squatPr: Float,
    val deadliftPr: Float
)