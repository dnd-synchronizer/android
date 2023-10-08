package ru.w0rng.dnd
import android.annotation.SuppressLint
import android.os.AsyncTask
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL



import ru.w0rng.dnd.MainActivity.Companion.clientId
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection

class RestApiClient {
    fun chengeStatus(status: Boolean) {
        println("RestApiClient chengeStatus")
        val data = "{\"room\": \"w0rng\", \"status\": \"$status\", \"client\": \"$clientId\"}"
        val url = "https://dnd.w0rng.ru/rooms/change_status/"
        PostRequestSender(url).sendPostRequest(data) { response ->
            println(response)
        }
    }

    private inner class PostRequestSender(private val url: String) {
        @OptIn(DelicateCoroutinesApi::class)
        fun sendPostRequest(jsonBody: String, onResponse: (String) -> Unit) {
            GlobalScope.launch(Dispatchers.IO) {
                val urlObj = URL(url)
                val connection = urlObj.openConnection() as HttpURLConnection

                try {
                    connection.requestMethod = "POST"
                    connection.setRequestProperty("Content-Type", "application/json")
                    connection.setRequestProperty("Accept", "application/json")
                    connection.doOutput = true

                    val outputStream = BufferedOutputStream(connection.outputStream)
                    outputStream.write(jsonBody.toByteArray())
                    outputStream.flush()

                    val response = StringBuilder()
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()

                    onResponse(response.toString())
                } finally {
                    connection.disconnect()
                }
            }
        }
    }

}