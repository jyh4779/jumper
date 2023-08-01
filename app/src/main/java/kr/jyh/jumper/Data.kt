package kr.jyh.jumper

var dZolaState:Int = 0
var fClickPower:Float = 0F
var fClickAngle:Float = 0F

var layoutHeight:Float = 0F
var layoutWidth:Float = 0F
var zolaHeight:Float = 0F
var zolaWidth:Float = 0F

const val MAXPOWERGAUGE:Int = 1000
const val DELAY:Long = 50

const val ZOLADROP:Int = 0
const val ZOLAJUMP:Int = 1
const val ZOLASTAY:Int = 2
const val ZOLADEATH:Int = 4

const val GRAVITY_DOWN_SPEED:Int = 20
const val WALL_DOWN_SPEED:Int = 10