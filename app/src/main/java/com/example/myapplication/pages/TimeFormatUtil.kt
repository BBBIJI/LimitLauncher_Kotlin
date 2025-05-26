package com.example.myapplication.pages

fun formatDuration(seconds: Int): String {
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        seconds < 60 -> "Just now"
        minutes < 60 -> "$minutes minute${if (minutes.toLong() == 1L) "" else "s"} ago"
        hours < 24 -> "$hours hour${if (hours.toLong() == 1L) "" else "s"} ago"
        else -> "$days day${if (days.toLong() == 1L) "" else "s"} ago"
    }
}
