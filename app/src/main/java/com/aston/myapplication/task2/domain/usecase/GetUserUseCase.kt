package com.aston.myapplication.task2.domain.usecase

import androidx.lifecycle.LiveData
import com.aston.myapplication.task2.domain.repository.UserRepository
import com.aston.myapplication.task2.domain.entity.User

class GetUserUseCase(val repository: UserRepository) {
    suspend operator fun invoke(userId: Int): LiveData<User> {
        return repository.getUser(userId)
    }
}