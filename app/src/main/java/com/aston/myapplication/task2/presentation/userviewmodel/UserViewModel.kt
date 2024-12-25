package com.aston.myapplication.task2.presentation.userviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aston.myapplication.task2.data.UserRepositoryImpl
import com.aston.myapplication.task2.domain.entity.User
import com.aston.myapplication.task2.domain.usecase.GetUserListUseCase
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val repository = UserRepositoryImpl
    private val getUserListUseCase = GetUserListUseCase(repository)

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> get() = _userList

    fun getUserList() {
        viewModelScope.launch {
            _userList.postValue(getUserListUseCase.invoke().value)
        }
    }
}