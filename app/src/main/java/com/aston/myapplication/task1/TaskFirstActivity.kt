package com.aston.myapplication.task1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aston.myapplication.R
import com.aston.myapplication.databinding.TaskFirstActivityBinding

class TaskFirstActivity : AppCompatActivity() {
    private val viewBinding: TaskFirstActivityBinding by lazy {
        TaskFirstActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fc_screen, FragmentA())
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newIntent(context: Context) =
            Intent(context, TaskFirstActivity::class.java)
    }
}