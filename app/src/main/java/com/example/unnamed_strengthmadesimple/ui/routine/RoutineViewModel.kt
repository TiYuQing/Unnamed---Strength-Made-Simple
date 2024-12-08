package com.example.unnamed_strengthmadesimple.ui.routine

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unnamed_strengthmadesimple.database.DatabaseProvider
import com.example.unnamed_strengthmadesimple.database.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoutineViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = DatabaseProvider.getDatabase(application).userDao()
        repository = UserRepository(userDao) // Pass UserDao to the repository
    }

    fun updateUserExperience(expGained: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = repository.getUser()
                if (user != null) {
                    val updatedUser = user.copy(experience = user.experience + expGained)
                    repository.updateUser(updatedUser)
                    withContext(Dispatchers.Main) {
                        callback(true) // Update successful
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        callback(false) // User not found
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback(false) // Handle errors
                }
            }
        }
    }

}