package com.example.unnamed_strengthmadesimple.database

import android.app.Application
import androidx.lifecycle.LiveData

class EvaluationRepository(private val evaluationDao: EvaluationDao) {

    fun getLatestEvaluationLiveData(): LiveData<Evaluation?> {
        return evaluationDao.getLatestEvaluation()
    }

    suspend fun insertEvaluation(evaluation: Evaluation) {
        evaluationDao.insertEvaluation(evaluation)
    }
}
