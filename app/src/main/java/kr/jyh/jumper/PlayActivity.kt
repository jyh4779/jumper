package kr.jyh.jumper

import android.content.Intent
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
        LIFECYCLE = LIFECYCLE_FIRST
        dZolaState = ZOLASTART
        score = 0

        playBinding = DataBindingUtil.setContentView(this,R.layout.activity_play)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout,StopFragment())
            .commit()

        playBinding.playLayout.setOnTouchListener(this)


        playContext = this
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        Log.d("PlayActivity", "[onWindowFocusChanged] Start")
        Log.d("PlayActivity", "[onWindowFocusChanged] [$LIFECYCLE]")


        if(LIFECYCLE != LIFECYCLE_FIRST) return

        setZolaInit()
        getTouchPointSize()

        zolaMotionClass.setZolaXY(playBinding.zola, layoutHeight-zolaHeight)

        wallClass.wallCoroutine()

        LIFECYCLE = LIFECYCLE_START
    }

    fun setZolaInit() {
        Log.d("PlayActivity", "[setZolaInit] Start")

        layoutHeight = playBinding.playLayout.height.toFloat()
        layoutWidth = playBinding.playLayout.width.toFloat()
        //layoutHeightDP = layoutHeight/ DENSITY
        //layoutWidthDP = layoutWidth/ DENSITY

        zolaHeight = layoutHeight/9
        zolaWidth = zolaHeight/3
        //zolaHeightDP = layoutHeightDP/7
        //zolaWidthDP = layoutWidthDP/9

        WALLWIDTHMIN = layoutWidth/WALLWIDTHMINRATE
        WALLWIDTHMAX = layoutWidth/WALLWIDTHMAXRATE


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
        Log.d("PlayActivity", "[setZolaInit] zolaX = ["+playBinding.zola.getX()+"]")
        Log.d("PlayActivity", "[setZolaInit] zolaY = ["+playBinding.zola.getY()+"]")
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
        if(LIFECYCLE == LIFECYCLE_PAUSE && dZolaState != ZOLADEATH) return false

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
                //TextView to test
                val text1 = jumpBtnClass.moveTouchPoint(x, y, playBinding.touchLine)
                //playBinding.playData = PlayData(text1)

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
        val param: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(width, height)
        //param.height = height*DENSITY.toInt()
        //param.width = width*DENSITY.toInt()
        v.layoutParams = param
    }

    fun callStopFragment(){
        if(dZolaState == ZOLADEATH) {
            fragmentData = "게임 종료"
            fragmentBinding.fragmentData = FragmentData(fragmentData, score.toString())
            //fragmentBinding.cancelBtn.setVisibility(View.GONE)
        }
        else {
            fragmentData = "일시 정지"
            fragmentBinding.fragmentData = FragmentData(fragmentData, score.toString())
        }
        playBinding.frameLayout.setVisibility(View.VISIBLE)
        playBinding.frameLayout.bringToFront()

        LIFECYCLE = LIFECYCLE_PAUSE
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        if(LIFECYCLE != LIFECYCLE_PAUSE) callStopFragment()
        else if(LIFECYCLE == LIFECYCLE_PAUSE && dZolaState != ZOLADEATH) setFragmentReturn("RESTART")
        //Toast.makeText(this, "[onBackPressed] Push Cancel Button", Toast.LENGTH_SHORT).show()
    }

    fun setFragmentReturn(ret:String){
        when(ret){
            "OK" -> {
                wallJob?.cancel()
                finish()
            }
            "CANCEL" -> {
                LIFECYCLE = LIFECYCLE_FIRST
                wallJob?.cancel()
                val intent = Intent(this, PlayActivity::class.java)
                startActivity(intent)
                finish()
            }
            "RESTART" -> {
                LIFECYCLE = LIFECYCLE_START
                playBinding.frameLayout.setVisibility(View.GONE)
            }
        }
    }

    override fun onStart() {
        Log.d("PlayActivity", "[onStart] start!!!!")
        Log.d("PlayActivity", "[onStart] [$LIFECYCLE]")
        if(LIFECYCLE != LIFECYCLE_FIRST) LIFECYCLE = LIFECYCLE_START
        super.onStart()
    }

    override fun onResume() {
        Log.d("PlayActivity", "[onResume] start!!!!")
        Log.d("PlayActivity", "[onResume] [$LIFECYCLE]")
        if(LIFECYCLE != LIFECYCLE_FIRST) LIFECYCLE = LIFECYCLE_START
        super.onResume()
    }

    override fun onPause() {
        Log.d("PlayActivity", "[onPause] start!!!!")
        Log.d("PlayActivity", "[onPause] [$LIFECYCLE]")
        if(LIFECYCLE != LIFECYCLE_FIRST) LIFECYCLE = LIFECYCLE_PAUSE
        super.onPause()
    }

    /*override fun onStop() {
        Log.d("PlayActivity", "[onStop] start!!!!")
        Log.d("PlayActivity", "[onStop] [$LIFECYCLE]")
        if(LIFECYCLE != LIFECYCLE_FIRST) LIFECYCLE = LIFECYCLE_PAUSE
        super.onStop()
    }*/

    /*override fun onRestart() {
        Log.d("PlayActivity", "[onRestart] start!!!!")
        Log.d("PlayActivity", "[onRestart] [$LIFECYCLE]")
        if(LIFECYCLE != LIFECYCLE_FIRST) LIFECYCLE = LIFECYCLE_START
        super.onRestart()
    }*/
}