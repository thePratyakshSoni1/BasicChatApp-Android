package com.pratyakshsoni.basicchatapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pratyakshsoni.basicchatapp.domain.repository.BackendRepo
import com.pratyakshsoni.basicchatapp.ui.theme.BasicChatAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicChatAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    val textResp = remember { mutableStateOf("") }
    val data = "{\"mail\":\"lalit@gmail.com\", \"password\":\"123456\"}"

    LaunchedEffect(key1 = true, block = {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//
//                val myUrl = URL("https://chatappbackend-service.onrender.com/login")
//
//                myUrl.openConnection().let {
//
//                    it.addRequestProperty("method", "POST")
//                    it.doOutput = true
//                    it.doInput = true
//                    it.addRequestProperty("cookie", "enKey=12; mod=123")
//                    it.getOutputStream().apply {
//                        write(data.toByteArray())
//                        flush()
//                    }
//
//                    it.getInputStream().readBytes().let{ bytes ->
//                        Log.e("RESPONSE", bytes.toString(Charset.defaultCharset()))
//                    }
//                    Log.d("END", "END OF METHODE")
//                }
//
//                delay(5000L)
//                val connection = (myUrl.openConnection() as HttpURLConnection)
//                connection.requestMethod
//                connection.requestMethod = "POST"
//                textResp.value = "Sending req..."
//                connection.outputStream.write(data.toByteArray())
//                connection.outputStream.flush()
//                textResp.value = "Req sent..."
//                val inputLine = connection.inputStream.readBytes()
//                textResp.value = "Response \n${inputLine.toString(Charset.defaultCharset())}"
//                Log.i("RESPONSE: ", inputLine.toString(Charset.defaultCharset()))
//
//            } catch (e: Exception) {
//                Log.e("ERROR", "Error Handler:\n ${e.message}")
//                Log.e("ERROR", "\nError Handler Localized:\n ${e.localizedMessage}")
//                e.printStackTrace()
//            }
//        }
    })
    LaunchedEffect(key1 = true, block = {
        CoroutineScope(Dispatchers.IO).launch{
            textResp.value = "Requesting..."
            textResp.value = BackendRepo().login( "lalit@gmail.com", "123456" )
        }
    })

    Text(
        text = textResp.value,
        modifier = modifier.padding(14.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicChatAppTheme {
        Greeting("Android")
    }
}