package com.example.unnamed_strengthmadesimple.database

import androidx.room.Embedded
import androidx.room.Relation

data class SessionWithExercises(
    @Embedded val workoutSession: Session,
    @Relation(
        parentColumn = "id",
        entityColumn = "sessionId"
    )
    val exercises: MutableList<ExerciseWithSetsAndReps>
)
