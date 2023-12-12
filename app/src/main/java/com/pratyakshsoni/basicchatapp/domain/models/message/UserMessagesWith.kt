package com.pratyakshsoni.basicchatapp.domain.models.message

import java.util.Date

data class UserMessagesWith(
    val person: String,
    val chat: List<UserMessage>,
    val lastUpdtaed: Date,
)



