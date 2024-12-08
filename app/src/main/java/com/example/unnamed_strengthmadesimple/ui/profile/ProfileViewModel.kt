package com.example.unnamed_strengthmadesimple.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unnamed_strengthmadesimple.database.DatabaseProvider
import com.example.unnamed_strengthmadesimple.database.Evaluation
import com.example.unnamed_strengthmadesimple.database.EvaluationRepository
import com.example.unnamed_strengthmadesimple.database.User
import com.example.unnamed_strengthmadesimple.database.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository
    private val evaluationRepository: EvaluationRepository

    val userLiveData: LiveData<User?>
    val evaluationLiveData: LiveData<Evaluation?>

    init {
        val database = DatabaseProvider.getDatabase(application)

        val userDao = database.userDao()
        userRepository = UserRepository(userDao)
        userLiveData = userRepository.getUserLiveData()

        val evaluationDao = database.evaluationDao()
        evaluationRepository = EvaluationRepository(evaluationDao)
        evaluationLiveData = evaluationRepository.getLatestEvaluationLiveData()
    }

    // Save evaluation data
    fun saveEvaluation(evaluation: Evaluation) {
        viewModelScope.launch(Dispatchers.IO) {
            evaluationRepository.insertEvaluation(evaluation)
        }
    }

    // Update user data (level, experience, STR)
    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUser(user)
        }
    }

    // Update user with calculated STR and total experience
    fun updateUserStats(user: User, evaluation: Evaluation) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedUser = user.copy(
                strength = calculateStrength(user, evaluation),
                experience = calculateTotalExperience(user)
            )
            userRepository.updateUser(updatedUser)
        }
    }

    // Calculate STR based on evaluation and user weight
    private fun calculateStrength(user: User, evaluation: Evaluation): Int {
        val benchRatio = evaluation.benchPr / user.weight
        val squatRatio = evaluation.squatPr / user.weight
        val deadliftRatio = evaluation.deadliftPr / user.weight

        val benchStr = (benchRatio / 3.0 * 33).coerceIn(0.0, 33.0)
        val squatStr = (squatRatio / 5.0 * 33).coerceIn(0.0, 33.0)
        val deadliftStr = (deadliftRatio / 6.0 * 33).coerceIn(0.0, 33.0)

        return (benchStr + squatStr + deadliftStr).toInt()
    }

    // Calculate total experience for the user (if required elsewhere)
    private fun calculateTotalExperience(user: User): Int {
        return user.experience // Placeholder
    }
}
