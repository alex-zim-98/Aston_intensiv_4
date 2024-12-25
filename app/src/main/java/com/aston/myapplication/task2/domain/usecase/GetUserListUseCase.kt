package com.aston.myapplication.task2.domain.usecase

import androidx.lifecycle.LiveData
import com.aston.myapplication.task2.domain.repository.UserRepository
import com.aston.myapplication.task2.domain.entity.User

class GetUserListUseCase(val repository: UserRepository) {
    suspend operator fun invoke(): LiveData<List<User>> {
        return repository.getUserList()
    }
}