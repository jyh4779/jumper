package kr.jyh.jumper

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kr.jyh.jumper.databinding.ActivityPlayBinding
import kr.jyh.jumper.databinding.ActivityScoreboardBinding
import kr.jyh.jumper.databinding.ActivityStartBinding
import kr.jyh.jumper.databinding.FragmentStopBinding

lateinit var playBinding: ActivityPlayBinding
lateinit var fragmentBinding: FragmentStopBinding
lateinit var scoreboardBinding: ActivityScoreboardBinding
lateinit var startBinding: ActivityStartBinding

lateinit var playContext:Context
lateinit var wallJob:Job

const val LIFECYCLE_START:Int = 0
const val LIFECYCLE_PAUSE:Int = 1
const val LIFECYCLE_FIRST:Int = 2

var LIFECYCLE:Int = LIFECYCLE_FIRST

var score:Int = 0
var playerName:String = "no name"

var dZolaState:Int = -1
var fClickPower:Float = 0F
var fClickAngle:Float = 0F

var layoutHeight:Float = 0F
var layoutWidth:Float = 0F
//var layoutHeightDP:Float = 0F
//var layoutWidthDP:Float = 0F

var zolaHeight:Float = 0F
var zolaWidth:Float = 0F
//var zolaHeightDP:Float = 0F
//var zolaWidthDP:Float = 0F

var WALLWIDTHMIN:Float = 0F
var WALLWIDTHMAX:Float = 0F
var WALLWIDTHMINRATE:Int = 5
var WALLWIDTHMAXRATE:Int = 3
const val WALLHEIGHT = 30

const val MAXPOWERGAUGE:Int = 1000
const val DELAY:Long = 50
const val WALLDELAY:Long = 1000

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

var fragmentData:String = "일시 정지"
