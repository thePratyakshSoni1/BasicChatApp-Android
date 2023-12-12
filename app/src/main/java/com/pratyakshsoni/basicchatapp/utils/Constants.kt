package com.pratyakshsoni.basicchatapp.utils

object Response {
    val isVerified: String = "isVerified"
    val msg: String = "msg"
    val enKey: String = "enKey"
    val isSuccess = "isSuccess"
    val status = "status"
    val isError = "isError"
    val error = "error"
    val errorCode = "errorCode"
    val public = "public"
    val private = "private"
    val mod = "mod"
    val mail = "mail"
    val password = "password"
    val email = "email"
    val userId = "userId"
    val message = "message"

}


object BackendEndpoints {
    const val login = "/login"
    const val signup = "/signup"
    const val generateLoginKey = "/genLoginKey"
    const val addUser = "/addUser"
    const val removeUser = "/removeUser"
    const val updateUser = "updateUser"
    const val verifyLoginCookies = "/verifyLogin"
    const val logout = "/logout"
    const val peoples = "/peoples"
    const val getTextHistory = "/textHistory"
    const val chatSocket = "/chatSocket"
    const val getUser = "/getUser"
    const val sendImageMsg = "/sendImageMsg"
}


object ResponseCookies{

    val loginToken = "logToken"
    val publicEncryptionKey = "enKey"
    val userId = "userId"
    val mod = "mod"
    val chatSessionId = "chatSession"
}

object ErrorCodes{
    val DIFFERENT_KEY_ENCRYPTION = 0
    val AUTH_DENEID = 1
    val MSG_MEDIA_NOT_FOUND = 2
    val INVALID_USER = 3

    val ANDROID_CALL_EXCEPTION =4
}

const val backendUrl = "https://chatappbackend-service.onrender.com"

fun getBackendUrlto(path: String): String{
    return "$backendUrl$path"
}