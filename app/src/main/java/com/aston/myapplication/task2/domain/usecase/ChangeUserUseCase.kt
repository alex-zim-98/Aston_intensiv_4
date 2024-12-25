package com.aston.myapplication.task2.domain.usecase

import com.aston.myapplication.task2.domain.repository.UserRepository
import com.aston.myapplication.task2.domain.entity.User

class ChangeUserUseCase(val repository: UserRepository) {
    suspend operator fun invoke(user: User) {
        repository.changeUser(user)
    }
}