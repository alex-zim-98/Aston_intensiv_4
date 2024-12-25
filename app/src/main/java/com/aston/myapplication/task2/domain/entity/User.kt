package com.aston.myapplication.task2.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val lastName: String,
    val image: Int,
    val phone: String,
): Parcelable