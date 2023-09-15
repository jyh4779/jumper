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
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.Charset
import java.util.Scanner

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

    fun loginToServer(email:String, name:String) {
        Log.d("SocketClientClass", "[loginToServer] start!!")

        CoroutineScope(Dispatchers.IO).async {
            sSocket = Socket(SERVER_IP, SERVER_PORT)

            var connected = true

            val reader = Scanner(sSocket.getInputStream())
            val writer = sSocket.getOutputStream()

            writer.write(("2\n").toByteArray(Charset.defaultCharset()))

            while(connected){
                //Log.d("SocketClientClass", "[loginToServer] Send Data [2]!!")
                val input = reader.nextLine()
                //Log.d("SocketClientClass", "[loginToServer] Recieve Data [$input]!!")
                if("END" in input){
                    Log.d("SocketClientClass", "[loginToServer] Recieve Data [$input]!!")
                    connected = false
                    writer.close()
                    reader.close()
                    sSocket.close()
                } else if("USER" in input){
                    Log.d("SocketClientClass", "[loginToServer] Recieve Data [$input]!!")
                    var send_text = email+";"+name
                    writer.write(("$send_text\n").toByteArray(Charset.defaultCharset()))
                } else if("-1" in input){
                    Log.d("SocketClientClass", "[loginToServer] Recieve Data [$input]!!")
                } else if("1" in input){
                    Log.d("SocketClientClass", "[loginToServer] Recieve Data [$input]!!")
                    Log.d("SocketClientClass", "[loginToServer] Login Success")
                } else if("0" in input){
                    Log.d("SocketClientClass", "[loginToServer] Recieve Data [$input]!!")
                    Log.d("SocketClientClass", "[loginToServer] First Login User")
                }
            }
        }
    }

    fun saveScoreToServer(email:String, score:Int) {
        Log.d("SocketClientClass", "[saveScoreToServer] start!!")

        CoroutineScope(Dispatchers.IO).async {
            sSocket = Socket(SERVER_IP, SERVER_PORT)

            var connected = true

            val reader = Scanner(sSocket.getInputStream())
            val writer = sSocket.getOutputStream()

            writer.write(("3\n").toByteArray(Charset.defaultCharset()))

            while(connected){
                //Log.d("SocketClientClass", "[loginToServer] Send Data [2]!!")
                val input = reader.nextLine()
                //Log.d("SocketClientClass", "[loginToServer] Recieve Data [$input]!!")
                if("END" in input) {
                }
            }
        }
    }
}