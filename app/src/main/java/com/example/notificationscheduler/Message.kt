package com.example.notificationscheduler

data class Message(
    val senderId: String, // Sender ka unique ID
    val content: String, // Message content
    val timestamp: Long // Message ka timestamp
)

