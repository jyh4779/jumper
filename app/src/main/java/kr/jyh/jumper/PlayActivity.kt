package kr.jyh.jumper

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil

class PlayActivity: AppCompatActivity(), View.OnTouchListener {

    private var touchPointWidth = 0
    private var touchPointHeight = 0

    val jumpBtnClass = JumpBtnClass()
    val zolaMotionClass = ZolaMotionClass()
    val wallClass = WallClass()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        Log.d("PlayActivity", "[onCreate] Start")

        playBinding = DataBindingUtil.setContentView(this,R.layout.activity_play)

        playBinding.playLayout.setOnTouchListener(this)

        playContext = this
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        Log.d("PlayActivity", "[onWindowFocusChanged] Start")

        setZolaInit()
        getTouchPointSize()

        zolaMotionClass.setZolaXY(playBinding.zola, layoutHeight-zolaHeight)
        wallClass.wallCoroutine()
    }

    fun setZolaInit() {
        Log.d("PlayActivity", "[setZolaInit] Start")

        val density = resources.displayMetrics.density

        layoutHeight = playBinding.playLayout.height/density
        layoutWidth = playBinding.playLayout.width/density
        zolaHeight = layoutHeight/10
        zolaWidth = layoutWidth/9
        WALLWIDTHMIN = layoutWidth/5
        WALLWIDTHMAX = layoutWidth/3

        val x = layoutWidth/2-zolaWidth/2
        val y = layoutHeight-zolaHeight

        Log.d("PlayActivity", "[setZolaInit] layoutHeight = $layoutHeight")
        Log.d("PlayActivity", "[setZolaInit] layoutWidth = $layoutWidth")
        Log.d("PlayActivity", "[setZolaInit] zolaHeight = $zolaHeight")
        Log.d("PlayActivity", "[setZolaInit] zolaWidth = $zolaWidth")
        Log.d("PlayActivity", "[setZolaInit] WALLWIDTHMIN = $WALLWIDTHMIN")
        Log.d("PlayActivity", "[setZolaInit] WALLWIDTHMAX = $WALLWIDTHMAX")
        Log.d("PlayActivity", "[setZolaInit] zolaX = $x")
        Log.d("PlayActivity", "[setZolaInit] zolaY = $y")

        //playBinding.zola.layoutParams.width = zolaWidth.toInt()
        //playBinding.zola.layoutParams.height = zolaHeight.toInt()
        setSize(playBinding.zola, zolaWidth.toInt(), zolaHeight.toInt())
        playBinding.zola.setX(x)
        playBinding.zola.setY(y)
        //playBinding.zola.setY(y)
    }

    fun getTouchPointSize(){
        Log.d("PlayActivity", "[getTouchPointSize] Start")

        touchPointWidth = playBinding.touchPoint.getWidth()
        touchPointHeight = playBinding.touchPoint.getHeight()

        Log.d("PlayActivity", "[getTouchPointSize] pointWidth = [$touchPointWidth]")
        Log.d("PlayActivity", "[getTouchPointSize] pointHeight = [$touchPointHeight]")
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

    fun setSize(v:View, width:Int, height:Int){
        val param: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT)
        param.height = height
        param.width = width

        v.layoutParams = param
    }
}