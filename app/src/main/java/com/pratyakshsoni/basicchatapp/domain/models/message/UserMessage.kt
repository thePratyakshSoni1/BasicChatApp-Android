package com.pratyakshsoni.basicchatapp.domain.models.message

import com.pratyakshsoni.basicchatapp.utils.MediaType
import java.util.Date

data class UserMessage(
    val id: Int,
    val data: String,
    val isMediaMessage: String,
    val mediaType: MediaType,
    val at: Date,
    val isSent: Boolean // True ? Sent : Received
)