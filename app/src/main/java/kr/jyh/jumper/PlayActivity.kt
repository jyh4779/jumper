package kr.jyh.jumper

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kr.jyh.jumper.databinding.ActivityPlayBinding

class PlayActivity: AppCompatActivity(), View.OnTouchListener {

    private lateinit var playBinding: ActivityPlayBinding
    private var touchPointWidth = 0
    private var touchPointHeight = 0

    var jumpBtnClass = JumpBtnClass()
    var zolaMotionClass = ZolaMotionClass()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        Log.d("PlayActivity", "[onCreate] Start")

        playBinding = DataBindingUtil.setContentView(this,R.layout.activity_play)

        playBinding.playLayout.setOnTouchListener(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        Log.d("PlayActivity", "[onWindowFocusChanged] Start")

        setZolaInit()
        getTouchPointSize()

        zolaMotionClass.setZolaXY(playBinding.zola, layoutHeight-zolaHeight)
    }

    fun setZolaInit() {
        Log.d("PlayActivity", "[setZolaInit] Start")

        layoutHeight = playBinding.playLayout.height.toFloat()
        layoutWidth = playBinding.playLayout.width.toFloat()
        zolaHeight = layoutHeight/10
        zolaWidth = layoutWidth/9
        val x = layoutWidth/2-zolaWidth/2
        val y = layoutHeight-zolaHeight

        Log.d("PlayActivity", "[setZolaInit] layoutHeight = $layoutHeight")
        Log.d("PlayActivity", "[setZolaInit] layoutWidth = $layoutWidth")
        Log.d("PlayActivity", "[setZolaInit] zolaHeight = $zolaHeight")
        Log.d("PlayActivity", "[setZolaInit] zolaWidth = $zolaWidth")
        Log.d("PlayActivity", "[setZolaInit] zolaX = $x")
        Log.d("PlayActivity", "[setZolaInit] zolaY = $y")

        playBinding.zola.setSize(zolaHeight.toInt(), zolaWidth.toInt())
        playBinding.zola.setX(x)
        playBinding.zola.setY(0F)
        //playBinding.zola.setY(y)
    }

    fun getTouchPointSize(){
        Log.d("PlayActivity", "[getTouchPointSize] Start")

        touchPointWidth = playBinding.touchPoint.getWidth()
        touchPointHeight = playBinding.touchPoint.getHeight()

        Log.d("PlayActivity", "[getTouchPointSize] pointWidth = [$touchPointWidth]")
        Log.d("PlayActivity", "[getTouchPointSize] pointHeight = [$touchPointHeight]")
    }

    fun View.setSize(dHeight: Int, dWidth:Int) {
        Log.d("PlayActivity", "[setSize] Start")

        val lp = layoutParams
        lp?.let {
            lp.height = dHeight
            lp.width = dWidth
            layoutParams = lp
        }
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        var x = event.getX()
        var y = event.getY()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                jumpBtnClass.startX = x
                jumpBtnClass.startY = y
                jumpBtnClass.touchPointX = x - touchPointWidth / 2
                jumpBtnClass.touchPointY = y - touchPointHeight / 2
                jumpBtnClass.downTouchPoint(playBinding.touchPoint, playBinding.touchLine)
            }

            MotionEvent.ACTION_MOVE -> {
                val text1 = jumpBtnClass.moveTouchPoint(x, y, playBinding.touchLine)
                playBinding.playData = PlayData(text1)

                zolaMotionClass.setZolaJumpMotion(playBinding.zola)
            }

            MotionEvent.ACTION_UP -> {
                jumpBtnClass.upTouchPoint(playBinding.touchPoint, playBinding.touchLine, x, y)

                zolaMotionClass.setZolaDefaultMotion(playBinding.zola)
            }
        }
        return true
    }
}