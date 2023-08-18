package kr.jyh.jumper.Socket

import android.net.ConnectivityManager
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

class SocketClientClass{
    /*suspend fun connServer():Socket{
        lateinit var socket:Socket

        Log.d("SocketClientClass", "[connServer] start!!")

            //Log.d("SocketClientClass", "[connServer] Coroutine Start!!")
            try {
                socket = Socket(SERVER_IP, SERVER_PORT)
            } catch (e: Exception) {
                e.stackTrace
            }
            //Log.d("SocketClientClass", "[connServer] Coroutine End!!")

        Log.d("SocketClientClass", "[connServer] end!!")
        return socket
    }*/

    fun selectWolrdRecord() {
        Log.d("SocketClientClass", "[selectWolrdRecord] start!!")

        CoroutineScope(Dispatchers.IO).async {
            sSocket = Socket(SERVER_IP, SERVER_PORT)

            val outStream = sSocket.outputStream
            val inStream = sSocket.getInputStream()
            var reader = BufferedReader(InputStreamReader(inStream))

            Log.d("SocketClientClass", "[selectWolrdRecord] Send Data [$SELECT_WORLD_RECORD]!!")
            outStream.write(SELECT_WORLD_RECORD.toByteArray())

            outStream.flush()
            outStream.close()

            val available = reader.readLine()
            Log.d("SocketClientClass", "[selectWolrdRecord] Recieve Data [$available]!!")

            /*if(available > 0){
                val dataArr = ByteArray(available)
                val data = String(dataArr)
                Log.d("SocketClientClass", "[selectWolrdRecord] $data")
            }*/
        }
    }
}