package com.example.unnamed_strengthmadesimple.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EvaluationDao {

    @Query("SELECT * FROM evaluation_table ORDER BY id DESC LIMIT 1")
    fun getLatestEvaluation(): LiveData<Evaluation?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvaluation(evaluation: Evaluation)
}