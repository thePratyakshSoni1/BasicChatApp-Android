package com.pratyakshsoni.basicchatapp.utils

import android.os.Build
import android.util.Base64
import android.util.Log
import java.nio.charset.Charset

data class RSAGoCredentials(
    val privateKey: Int,
    val publicKey: Int,
    val modulous: Int,
)

private fun generatePrimeNums(): List<Int>{

    val primeNums = mutableListOf<Int>()
    var seive = arrayOfNulls<Boolean?>(250)
    seive.fill(true)
    seive[0] = false
    seive[1] = false

    for( i in 2..249){
        var j: Int =i*2
        while(j<250){
            seive[j] = false
            j+= i
        }
    }

    var i=0
    seive.forEach {
        if(it!!) primeNums.add(i)
        i++
    }

    return primeNums.subList(10, primeNums.size)


}

private fun setKeys(primeNums: List<Int>): RSAGoCredentials{
    val prime1 = primeNums.random()
    val prime2 = primeNums.random()

    val modulus = prime1*prime2
    val fi = (prime1-1)*(prime2-1)
    var e=2
    var d=2

    while(true){
        if(gcd(e, fi) == 1){
            break
        }
        e+=1
    }
    val publicKey = e
    while(true){
        if((d*e)%fi == 1){
            break
        }
        d+= 1
    }
    val privateKey= d

    return RSAGoCredentials(
        privateKey,
        publicKey,
        modulus
    )

}

private fun gcd(n1:Int, n2:Int): Int{
    var gcd = 1

    var i = 1
    while (i <= n1 && i <= n2) {
        // Checks if i is factor of both integers
        if (n1 % i == 0 && n2 % i == 0)
            gcd = i
        ++i
    }
    return gcd
}

private fun encrypt(msg: Int, publicKey: Int, modulous: Int): Int{

    var e = publicKey
    var encryptedTxt = 1
    while(e>0){
        encryptedTxt *= msg
        encryptedTxt %= modulous
        e -= 1
    }

    return encryptedTxt

}

private fun decrypt(msg:Int, privateKey: Int, modulous: Int): Int{
    var d= privateKey
    var decryptedMsg = 1
    while(d>0){
        decryptedMsg *= msg
        decryptedMsg %= modulous
        d-=1
    }
    return decryptedMsg
}

private fun encode(msg: String, modulous: Int, publicKey: Int, ): IntArray{
    val encoded = mutableListOf<Int>()

    for( letter in msg ){
        encoded.add(
            encrypt(letter.code, publicKey, modulous)
        )
    }

    return encoded.toIntArray()
}

private fun decode(msg: List<Int>, modulous: Int, privateKey: Int): String{
    var s = ""
    for( num in msg){
        s += decrypt(num, privateKey, modulous).toChar()
    }
    return s
}

fun encryptData(data: String, publicKey:Int, modulous: Int): String{

    val rsaEncoded = encode(data, modulous, publicKey).toList().toString()
    val rsaEncodedBytes = rsaEncoded.substring(1, rsaEncoded.length-1)
    val base64Encode = Base64.encodeToString(rsaEncodedBytes.toByteArray(), Base64.DEFAULT)

    Log.i("ENCRYPTION", "RSA: $rsaEncoded")
    Log.i("ENCRYPTION", "B64: ${base64Encode}")

    return base64Encode

}

fun decryptData(data: String, privateKey:Int,  modulous: Int): String{

    val base64Decoded = Base64.decode(data.toByteArray(), Base64.DEFAULT)
    val datBytes = mutableListOf<Int>()

    base64Decoded.toString(Charset.defaultCharset()).let{
        it.subSequence(1, it.length-2).apply {
            split(", ").forEach { numStr ->
                datBytes.add(numStr.toInt())
            }
        }
    }

    return decode(datBytes, modulous, privateKey)

}