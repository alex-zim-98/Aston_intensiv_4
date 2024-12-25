package com.aston.myapplication.task2.presentation.userviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aston.myapplication.task2.data.UserRepositoryImpl
import com.aston.myapplication.task2.domain.entity.User
import com.aston.myapplication.task2.domain.usecase.ChangeUserUseCase
import com.aston.myapplication.task2.domain.usecase.GetUserUseCase
import kotlinx.coroutines.launch

class UserItemViewModel: ViewModel() {
    private var currentIndex = 0
    private val _currentImage = MutableLiveData<Int>()
    val currentImage get() = _currentImage

    private val repository = UserRepositoryImpl
    private val getUserUseCase = GetUserUseCase(repository)
    private val changeUserUseCase = ChangeUserUseCase(repository)

    val listAvatars = repository.listAvatar

    private val _shouldClose = MutableLiveData<Unit>()
    val shouldClose: LiveData<Unit> get() = _shouldClose

    private val _userLD = MutableLiveData<User>()
    val userLD: LiveData<User> get() = _userLD

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName get() = _errorInputName

    private val _errorInputLastname = MutableLiveData<Boolean>()
    val errorInputLastname get() = _errorInputLastname

    private val _errorInputPhone = MutableLiveData<Boolean>()
    val errorInputPhone get() = _errorInputPhone

    fun getUser(userId: Int) {
        viewModelScope.launch {
                val user = getUserUseCase(userId)
                user.value?.let {
                    _userLD.postValue(it)
                    setFirstImage(it)
                }
        }
    }

    fun nextAvatar() {
        val index = countIndex(_currentImage.value?: NULLABLE_VALUE)
        currentIndex = (index + 1) % listAvatars.size

        _currentImage.value = listAvatars[currentIndex]
    }

    fun previousAvatar() {
        val index = countIndex(_currentImage.value?: NULLABLE_VALUE)
        currentIndex = if(index > 0) (index - 1) % listAvatars.size else listAvatars.size - 1

        _currentImage.value = listAvatars[currentIndex]
    }

    private fun setFirstImage(user: User) {
        _currentImage.value = user.image
    }

    private fun countIndex(resId: Int): Int {
        return listAvatars.indexOf(resId)
    }

    private fun isExistAvatar(resId: Int): Boolean {
        return listAvatars.any { it == resId }
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputLastname() {
        _errorInputLastname.value = false
    }

    fun resetErrorInputPhone() {
        _errorInputPhone.value = false
    }

    fun saveChanges(
        imageRes: String?,
        nameInput: String?,
        lastNameInput: String?,
        phoneInput: String?,
    ) {
        val image = parseImage(imageRes)
        val name = parseField(nameInput)
        val lastName = parseField(lastNameInput)
        val phone = parseField(phoneInput)

        if (!validateFields(name, lastName, phone) && isExistAvatar(image)) {
            _userLD.value?.let {
                viewModelScope.launch {
                    changeUserUseCase(it.copy(
                        name = name,
                        lastName = lastName,
                        phone = phone,
                        image = image
                    ))
                    _shouldClose.value = Unit
                }
            }
        }
    }

    private fun parseImage(resId: String?): Int {
        return resId?.trim()?.toInt() ?:
            throw RuntimeException("ResId $resId is incorrect")
    }

    private fun parseField(field: String?): String {
        return field?.trim() ?:
            throw RuntimeException("Field is equals null")
    }

    private fun validateFields(
        name: String,
        lastName: String,
        phone: String,
    ): Boolean {
        var result = false
        if (name.length < 5) {
            result = true
            _errorInputName.value = true
        }
        if (lastName.length < 3) {
            result = true
            _errorInputLastname.value = true
        }
        if (phone.length < 6) {
            result = true
            _errorInputPhone.value = true
        }

        return result
    }

    companion object {
        private const val NULLABLE_VALUE = 0
    }
}