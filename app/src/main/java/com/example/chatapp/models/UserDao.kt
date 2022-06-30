package com.example.chatapp.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAll() : List<User>

    @Query("SELECT * FROM users WHERE uid = :uid")
    fun findById(uid : String) : User

    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)
}