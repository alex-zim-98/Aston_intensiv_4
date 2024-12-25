package com.aston.myapplication.task2.presentation.userlist

import androidx.recyclerview.widget.DiffUtil
import com.aston.myapplication.task2.domain.entity.User

class UserListDiffUtil: DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}