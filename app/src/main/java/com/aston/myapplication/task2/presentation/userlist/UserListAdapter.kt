package com.aston.myapplication.task2.presentation.userlist
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.aston.myapplication.R
import com.aston.myapplication.databinding.UserItemBinding
import com.aston.myapplication.task2.domain.entity.User

class UserListAdapter: ListAdapter<User, UserListViewHolder>(UserListDiffUtil()) {
    var onClickUserListener: ((user: User) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val viewBinding = UserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val viewHolder = UserListViewHolder(viewBinding)
        viewBinding.root.rootView.setOnClickListener {
            val user = currentList[viewHolder.adapterPosition]
            onClickUserListener?.invoke(user)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = currentList[holder.adapterPosition]
        with(holder.viewBinding) {
            val context = holder.itemView.context
            ivAvatar.setImageDrawable(ContextCompat.getDrawable(context, user.image))
            tvName.text = getString(context, R.string.name, user.name)
            tvLastName.text = getString(context, R.string.lastname, user.lastName)
            tvPhone.text = getString(context, R.string.phone, user.phone)
        }
    }

    private fun getString(context: Context, resId: Int, value: String): String {
        return String.format(ContextCompat.getString(context, resId), value)
    }

}