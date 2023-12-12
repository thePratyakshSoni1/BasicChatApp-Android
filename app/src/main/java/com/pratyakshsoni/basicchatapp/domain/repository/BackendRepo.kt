package com.pratyakshsoni.basicchatapp.domain.repository

import android.util.Log
import com.pratyakshsoni.basicchatapp.domain.models.ResponseModel
import com.pratyakshsoni.basicchatapp.domain.models.toGenerateEncKeyResponse
import com.pratyakshsoni.basicchatapp.domain.models.toLoginResponse
import com.pratyakshsoni.basicchatapp.utils.BackendEndpoints
import com.pratyakshsoni.basicchatapp.utils.ErrorCodes
import com.pratyakshsoni.basicchatapp.utils.RSAGoCredentials
import com.pratyakshsoni.basicchatapp.utils.Response
import com.pratyakshsoni.basicchatapp.utils.boolean
import com.pratyakshsoni.basicchatapp.utils.encryptData
import com.pratyakshsoni.basicchatapp.utils.getBackendUrlto
import com.pratyakshsoni.basicchatapp.utils.int
import com.pratyakshsoni.basicchatapp.utils.string
import org.json.JSONObject
import java.net.URL
import java.nio.charset.Charset

class BackendRepo {

    fun getLoginKeys(): ResponseModel.GenerateEncKeyResponse {

        try {
            val myUrl = URL(getBackendUrlto(BackendEndpoints.generateLoginKey))
            var data: JSONObject? = null
            myUrl.openConnection().let {

                it.addRequestProperty("method", "POST")
                it.doOutput = false
                it.doInput = true

                it.getInputStream().readBytes().let { bytes ->
                    data = JSONObject(bytes.toString(Charset.defaultCharset()))
                    Log.i("RESPONSE JSON", data!!.toString())
                }
                Log.i("END", "END OF GENKEY METHODE")
            }

            return data!!.toGenerateEncKeyResponse()

        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseModel.GenerateEncKeyResponse(
                null, null, null, true
            )
        }

    }

    fun requestLogin(mail: String, password: String): ResponseModel.LoginResponse {

        try {
            val enKeys = getLoginKeys()
            var response = ""

            if (enKeys.public != null || enKeys.mod != null || enKeys.isError) {
                val myUrl = URL(getBackendUrlto(BackendEndpoints.login))
                val data = JSONObject(
                    mutableMapOf(
                        "mail" to encryptData(
                            mail,
                            enKeys.public!!,
                            enKeys.mod!!
                        ), //Nullability checked above
                        "password" to encryptData(password, enKeys.public, enKeys.mod),
                        "logEncKeyWithMod" to "${enKeys.public}${enKeys.mod}"
                    ) as Map<Any, Any>
                )

                Log.e("JSON", "$data")
                Log.e("JSON", data.toString())

                myUrl.openConnection().let {

                    it.addRequestProperty("method", "POST")
                    it.doOutput = true
                    it.doInput = true
                    it.getOutputStream().apply {
                        write(data.toString().toByteArray())
                        flush()
                    }

                    it.getInputStream().readBytes().let { bytes ->
                        Log.i("RESPONSE", bytes.toString(Charset.defaultCharset()))
                        Log.e("RESPONSE", "Cooks: ${it.headerFields}")
                        response = bytes.toString(Charset.defaultCharset())
                    }

                    return JSONObject(response).toLoginResponse()
                }

            } else {
                return ResponseModel.LoginResponse(
                    false,
                    "failed",
                    ErrorCodes.DIFFERENT_KEY_ENCRYPTION,
                )
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseModel.LoginResponse(false, "exception", ErrorCodes.ANDROID_CALL_EXCEPTION)
        }

    }

}