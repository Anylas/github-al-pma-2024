package com.example.vanocniaplikace017

data class DayItem(
    val day: Int,
    val isUnlocked: Boolean,
    val content: String,
    val soundResId: Int? = null
)