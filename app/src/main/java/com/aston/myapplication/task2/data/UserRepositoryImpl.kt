package com.aston.myapplication.task2.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aston.myapplication.R
import com.aston.myapplication.task2.domain.entity.User
import com.aston.myapplication.task2.domain.repository.UserRepository
import java.util.TreeSet

object UserRepositoryImpl: UserRepository {
    private val listUser = TreeSet<User> { p0, p1 -> p0.id.compareTo(p1.id) }
    private val _listUserLD = MutableLiveData<List<User>>()
    private val _userLD = MutableLiveData<User>()

    val listAvatar = listOf(
        R.drawable.s1,
        R.drawable.s2,
        R.drawable.s3,
        R.drawable.s4
    )

    init {
        for (i in 1..4) {
            listUser.add(
                User(i, "Name $i", "Lastname $i", listAvatar[i - 1], "+7(906)441-53-4$i")
            )
        }
    }

    override suspend fun changeUser(user: User) {
        listUser.remove(user)
        listUser.add(user)
        update()
    }

    override suspend fun getUser(userId: Int): LiveData<User> {
        val user = listUser.find { it.id == userId } ?:
            throw RuntimeException("Undefined id")
        _userLD.value = user
        return _userLD
    }

    override suspend fun getUserList(): LiveData<List<User>> {
        update()
        return _listUserLD
    }

    private fun update() {
        _listUserLD.value = listUser.toList()
    }
}