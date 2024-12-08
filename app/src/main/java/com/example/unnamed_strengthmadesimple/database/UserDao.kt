package com.example.unnamed_strengthmadesimple.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    //Makes sure conflicts get replaced
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(user: User)

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM user_table WHERE id = 1")
    fun getUser(): User?

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM user_table WHERE id = :id LIMIT 1")
    fun getUserLiveData(id: Int = 1): LiveData<User?>
}

