package kr.jyh.jumper.Socket

import java.net.Socket

const val SERVER_IP:String = "49.50.165.222"
//const val SERVER_IP:String = "127.0.0.1"
const val SERVER_PORT:Int = 9999

const val SELECT_WORLD_RECORD:String = "1"
const val LOGIN_TO_SERVER:String = "2"

lateinit var sSocket:Socket