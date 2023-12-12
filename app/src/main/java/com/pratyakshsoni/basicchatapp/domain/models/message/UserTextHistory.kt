package com.pratyakshsoni.basicchatapp.domain.models.message

import java.util.Date

data class UserTextHistory (
    val userId: String,
    val lastUpdated: Date,
    val messages: List<UserMessagesWith>

)