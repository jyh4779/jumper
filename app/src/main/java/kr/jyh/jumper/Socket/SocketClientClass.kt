package kr.jyh.jumper.Socket

import android.net.ConnectivityManager
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kr.jyh.jumper.SCOREBOARDDATADELAY
import kr.jyh.jumper.ScoreBoardActivity
import kr.jyh.jumper.score
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

    fun selectWolrdRecord(): Array<Array<String>> {
        Log.d("SocketClientClass", "[selectWolrdRecord] start!!")

        var scoreDataArr = arrayOf(arrayOf(" "," "," "),arrayOf(" "," "," "),arrayOf( " ", " ", " "),
            arrayOf( " ", " ", " "),arrayOf( " ", " ", " "),arrayOf( " ", " ", " "),arrayOf( " ", " ", " ")
            ,arrayOf( " ", " ", " "),arrayOf( " ", " ", " "),arrayOf( " ", " ", " "))

        //CoroutineScope(Dispatchers.IO).async {
            sSocket = Socket(SERVER_IP, SERVER_PORT)

            var connected = true

            val reader = Scanner(sSocket.getInputStream())
            val writer = sSocket.getOutputStream()

            writer.write(("SCOREBOARD\n").toByteArray(Charset.defaultCharset()))

            while(connected){
                //Log.d("SocketClientClass", "[loginToServer] Send Data [2]!!")
                var input = reader.nextLine()
                //Log.d("SocketClientClass", "[loginToServer] Recieve Data [$input]!!")
                if("END" in input){
                    Log.d("SocketClientClass", "[selectWolrdRecord] Recieve Data [$input]!!")
                    connected = false
                    writer.close()
                    reader.close()
                    sSocket.close()
                } else if("SCOREDATA" in input){
                    Log.d("SocketClientClass", "[selectWolrdRecord] Recieve Data [$input]!!")
                    val stringList = input.split(";")
                    val dataCnt = stringList[1]
                    scoreDataArr[dataCnt.toInt()][0] = stringList[2]
                    scoreDataArr[dataCnt.toInt()][1] = stringList[3]
                    scoreDataArr[dataCnt.toInt()][2] = stringList[4]
                    Log.d("SocketClientClass", "[selectWolrdRecord] Recieve Data date[${scoreDataArr[dataCnt.toInt()][0]}]!!")
                    Log.d("SocketClientClass", "[selectWolrdRecord] Recieve Data name[${scoreDataArr[dataCnt.toInt()][1]}]!!")
                    Log.d("SocketClientClass", "[selectWolrdRecord] Recieve Data score[${scoreDataArr[dataCnt.toInt()][2]}]!!")

                    var send_text = "DONE"
                    writer.write(("$send_text\n").toByteArray(Charset.defaultCharset()))
                }
            }
            for(i in 0..9){
                Log.d("SocketClientClass", "[selectWolrdRecord] Recieve Data date[${scoreDataArr[i][0]}]!!")
                Log.d("SocketClientClass", "[selectWolrdRecord] Recieve Data date[${scoreDataArr[i][1]}]!!")
                Log.d("SocketClientClass", "[selectWolrdRecord] Recieve Data date[${scoreDataArr[i][2]}]!!")
            }
        //}

        SCOREBOARDDATADELAY = 1
        return scoreDataArr
    }

    fun loginToServer(email:String, name:String) {
        Log.d("SocketClientClass", "[loginToServer] start!!")

        CoroutineScope(Dispatchers.IO).async {
            sSocket = Socket(SERVER_IP, SERVER_PORT)

            var connected = true

            val reader = Scanner(sSocket.getInputStream())
            val writer = sSocket.getOutputStream()

            writer.write(("LOGIN\n").toByteArray(Charset.defaultCharset()))

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

            writer.write(("SCORE\n").toByteArray(Charset.defaultCharset()))

            while(connected){
                //Log.d("SocketClientClass", "[loginToServer] Send Data [2]!!")
                val input = reader.nextLine()
                //Log.d("SocketClientClass", "[loginToServer] Recieve Data [$input]!!")
                if("END" in input) {
                    Log.d("SocketClientClass", "[saveScoreToServer] Recieve Data [$input]!!")
                    connected = false
                    writer.close()
                    reader.close()
                    sSocket.close()
                } else if("INSERT" in input){
                    Log.d("SocketClientClass", "[saveScoreToServer] Recieve Data [$input]!!")
                    var send_text = email+";"+score
                    writer.write(("$send_text\n").toByteArray(Charset.defaultCharset()))

                }
            }
        }
    }
}