package kr.jyh.jumper

import android.content.Context
import kr.jyh.jumper.databinding.ActivityPlayBinding

lateinit var playBinding: ActivityPlayBinding

lateinit var playContext:Context

var dZolaState:Int = -1
var fClickPower:Float = 0F
var fClickAngle:Float = 0F

var layoutHeight:Float = 0F
var layoutWidth:Float = 0F
var zolaHeight:Float = 0F
var zolaWidth:Float = 0F

var WALLWIDTHMIN:Float = 0F
var WALLWIDTHMAX:Float = 0F
const val WALLHEIGHT = 30

const val MAXPOWERGAUGE:Int = 1000
const val DELAY:Long = 50
const val WALLDELAY:Long = 5000

/*첫 점프 전*/
const val ZOLASTART:Int = -1
/*떨어지는 중*/
const val ZOLADROP:Int = 0
/*올라가는 중*/
const val ZOLAJUMP:Int = 1
/*벽 밟고 있는 중*/
const val ZOLASTAY:Int = 2
/*죽음*/
const val ZOLADEATH:Int = 4

/**/
const val GRAVITY_DOWN_SPEED:Int = 20
/**/
const val WALL_DOWN_SPEED:Int = 10
