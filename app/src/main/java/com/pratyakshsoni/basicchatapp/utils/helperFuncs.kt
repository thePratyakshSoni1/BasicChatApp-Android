package com.pratyakshsoni.basicchatapp.utils

import org.json.JSONException
import org.json.JSONObject

fun JSONObject.string(propertyName: String): String?{
    return try {
        getString(propertyName)
    }catch (e: JSONException){
        null
    }
}
fun JSONObject.boolean(propertyName: String): Boolean?{
    return try {
        getBoolean(propertyName)
    }catch (e: JSONException){
        null
    }
}
fun JSONObject.int(propertyName: String): Int?{
    return try {
        getInt(propertyName)
    }catch (e: JSONException){
        null
    }
}

/** Returns double value of give propertyName if it doesn't exists returns null */
fun JSONObject.double(propertyName: String): Double?{
    return try {
        getDouble(propertyName)
    }catch (e: JSONException){
        null
    }
}