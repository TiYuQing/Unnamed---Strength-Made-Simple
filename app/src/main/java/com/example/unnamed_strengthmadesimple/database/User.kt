package com.example.unnamed_strengthmadesimple.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val id: Int = 1,
    val name: String,
    val weight: Int,
    val level: Int = 0,
    val experience: Int = 0,
    val strength: Int = 0,

)