package com.example.unnamed_strengthmadesimple.database

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    fun getUser(): User? {
        return userDao.getUser() // Query user with id=1
    }

    fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    fun getUserLiveData(): LiveData<User?> {
        return userDao.getUserLiveData()
    }
}

