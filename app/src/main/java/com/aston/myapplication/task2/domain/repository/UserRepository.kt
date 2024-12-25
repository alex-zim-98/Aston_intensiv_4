package com.aston.myapplication.task2.domain.repository

import androidx.lifecycle.LiveData
import com.aston.myapplication.task2.domain.entity.User

interface UserRepository {
    suspend fun changeUser(user: User)
    suspend fun getUser(userId: Int): LiveData<User>
    suspend fun getUserList(): LiveData<List<User>>
}