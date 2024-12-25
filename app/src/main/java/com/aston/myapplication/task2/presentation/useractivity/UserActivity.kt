package com.aston.myapplication.task2.presentation.useractivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aston.myapplication.R
import com.aston.myapplication.databinding.ActivityUserBinding
import com.aston.myapplication.task2.presentation.userlist.UserListFragment

class UserActivity : AppCompatActivity() {
    private val viewBinding by lazy {
        ActivityUserBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fcvUserList, UserListFragment.newInstance())
            .commit()
    }

    companion object {
        fun newIntent(context: Context) =
            Intent(context, UserActivity::class.java)
    }
}