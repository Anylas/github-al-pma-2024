package com.example.vanocniaplikace017

data class DayItem(
    val day: Int? = null,
    val isUnlocked: Boolean = false,
    val content: String = "",
    val soundResId: Int? = null,
    val showTree: Boolean = false
)