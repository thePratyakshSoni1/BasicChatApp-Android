package com.pratyakshsoni.basicchatapp.domain.models

import com.pratyakshsoni.basicchatapp.utils.Response
import com.pratyakshsoni.basicchatapp.utils.boolean
import com.pratyakshsoni.basicchatapp.utils.int
import com.pratyakshsoni.basicchatapp.utils.string
import org.json.JSONObject

class ResponseModel {

    data class LoginResponse(
        val isSuccess: Boolean?,
        val status: String?,
        val errorCode: Int? = null,
        val enKey: Int? = null,
        val mod: Int? = null
    )

    data class GenerateEncKeyResponse(
        val public: Int? = null,
        val private: Int? = null,
        val mod: Int?,
        val isError: Boolean = false
    )

    data class BasicResponse(
        val isSuccess: Boolean?, //optional
        val msg: String?,
        val error: Int? //optional
    )

    data class CookieVerificationResponse(
        val isVerified: Boolean?,
        val message: String?
    )


}

fun JSONObject.toLoginResponse(): ResponseModel.LoginResponse {
    return ResponseModel.LoginResponse(
        boolean(Response.isSuccess),
        string(Response.status),
        int(Response.errorCode),
        int(Response.enKey),
        int(Response.mod),
    )
}

fun JSONObject.toGenerateEncKeyResponse(): ResponseModel.GenerateEncKeyResponse{

    return ResponseModel.GenerateEncKeyResponse(
        int(Response.public),
        int(Response.private),
        int(Response.mod),
    )

}

fun JSONObject.toBasicResponse(): ResponseModel.BasicResponse{

    var msg : String? = null
    string(Response.message).let{ if( it != null ) msg = it }
    string(Response.msg).let{ if(it != null) msg = it }

    return ResponseModel.BasicResponse(
        boolean(Response.isSuccess),
        msg,
        int(Response.error)
    )
}


fun JSONObject.toCookieVerificationResponse(): ResponseModel.CookieVerificationResponse{

    return ResponseModel.CookieVerificationResponse(
        boolean( Response.isVerified ),
        string( Response.message )
    )

}