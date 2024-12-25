package com.aston.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aston.myapplication.databinding.ActivityStartBinding
import com.aston.myapplication.task1.TaskFirstActivity
import com.aston.myapplication.task2.presentation.UserActivity

class StartActivity : AppCompatActivity() {
    private val viewBinding: ActivityStartBinding by lazy {
        ActivityStartBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        clickListeners()
    }

    private fun clickListeners() {
        viewBinding.btnTask1.setOnClickListener {
            startActivity(TaskFirstActivity.newIntent(this))
        }

        viewBinding.btnTask2.setOnClickListener {
            startActivity(UserActivity.newIntent(this))
        }
    }
}