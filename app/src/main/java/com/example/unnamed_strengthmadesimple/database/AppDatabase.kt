package com.example.unnamed_strengthmadesimple.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Routine::class, RoutineDay::class, Exercise::class, Session::class, SetsAndReps::class, SessionExerciseCrossRef::class, Evaluation::class], version = 15, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun sessionDao(): SessionDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun setsAndRepsDao(): SetsAndRepsDao
    abstract fun sessionExerciseDao(): SessionExerciseDao
    abstract fun evaluationDao(): EvaluationDao
}
