package kr.jyh.jumper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kr.jyh.jumper.fragmentData

class PlayActivity: AppCompatActivity(), View.OnTouchListener {
    private var mRewardedAd: RewardedAd? = null

    private var touchPointWidth = 0
    private var touchPointHeight = 0

    val jumpBtnClass = JumpBtnClass()
    val zolaMotionClass = ZolaMotionClass()
    val wallClass = WallClass()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        Log.d("PlayActivity", "[onCreate] Start")
        playBinding = DataBindingUtil.setContentView(this, R.layout.activity_play)

        MobileAds.initialize(this)
        val adRequst = AdRequest.Builder().build()
        playBinding.adBannerView.loadAd(adRequst)

        LIFECYCLE = LIFECYCLE_FIRST
        dZolaState = ZOLASTART
        score = 0
        rewardAdFlag = 0
        remainWallCnt = 0
        lastWallId = 0


        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, StopFragment())
            .commit()

        playBinding.playLayout.setOnTouchListener(this)

        playContext = this

        //rewarded ad initial
        MobileAds.initialize(this) {
                initializationStatus -> loadRewardAd()
        }
        setRewardedAdCallback()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        Log.d("PlayActivity", "[onWindowFocusChanged] Start")
        Log.d("PlayActivity", "[onWindowFocusChanged] [$LIFECYCLE]")

        if(LIFECYCLE != LIFECYCLE_FIRST) return

        setZolaInit()
        getTouchPointSize()

        zolaMotionClass.setZolaXY(playBinding.zola, layoutHeight - zolaHeight)

        wallClass.wallCoroutine()

        LIFECYCLE = LIFECYCLE_START
    }

    fun setZolaInit() {
        Log.d("PlayActivity", "[setZolaInit] Start")

        layoutHeight = playBinding.playLayout.height.toFloat()
        layoutWidth = playBinding.playLayout.width.toFloat()
        //layoutHeightDP = layoutHeight/ DENSITY
        //layoutWidthDP = layoutWidth/ DENSITY

        zolaHeight = layoutHeight /9
        zolaWidth = zolaHeight /3
        //zolaHeightDP = layoutHeightDP/7
        //zolaWidthDP = layoutWidthDP/9

        WALLWIDTHMIN = layoutWidth / WALLWIDTHMINRATE
        WALLWIDTHMAX = layoutWidth / WALLWIDTHMAXRATE


        val x = layoutWidth /2- zolaWidth /2
        val y = layoutHeight - zolaHeight

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
        Log.d("PlayActivity", "[setZolaInit] zolaX = ["+ playBinding.zola.getX()+"]")
        Log.d("PlayActivity", "[setZolaInit] zolaY = ["+ playBinding.zola.getY()+"]")
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
        var replayBtnText = "다시하기"
        if(dZolaState == ZOLADEATH) {
            fragmentData = "게임 종료"
            Log.d("PlayActivity", "[callStopFragment] rewardAdFlag[$rewardAdFlag].")

            if(rewardAdFlag != 1) replayBtnText = "광고보고\n이어하기"
            Log.d("PlayActivity", "[callStopFragment] replayBtnText[$replayBtnText].")
        }
        else fragmentData = "일시 정지"

        fragmentBinding.fragmentData = FragmentData(fragmentData, replayBtnText, score.toString())
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
                Log.d("PlayActivity","[setFragmentReturn] RemainWall[$remainWallCnt], LastId[$lastWallId]")
                //wallClass.removeRemainAllWall()
                finish()
            }
            "CANCEL" -> {
                LIFECYCLE = LIFECYCLE_FIRST
                wallJob?.cancel()
                Log.d("PlayActivity","[setFragmentReturn] RemainWall[$remainWallCnt], LastId[$lastWallId]")
                //wallClass.removeRemainAllWall()
                val intent = Intent(this, PlayActivity::class.java)
                startActivity(intent)
                finish()
            }
            "RESTART" -> {
                LIFECYCLE = LIFECYCLE_START
                playBinding.frameLayout.setVisibility(View.GONE)
            }
            "ADSTART" -> {
                mRewardedAd?.let { ad ->
                    ad.show(this, OnUserEarnedRewardListener { rewardItem ->
                        Log.d("PlayActivity", "[setFragmentReturn] User earned the reward[$rewardItem].")
                        rewardAdFlag = 1
                        Log.d("PlayActivity", "[setFragmentReturn] rewardAdFlag[$rewardAdFlag].")

                        playBinding.frameLayout.setVisibility(View.GONE)
                        playBinding.zola.setY(layoutHeight - zolaHeight)
                        zolaMotionClass.setZolaDefaultMotion(playBinding.zola)
                        LIFECYCLE = LIFECYCLE_START
                        dZolaState = ZOLASTART
                        zolaMotionClass.setZolaXY(playBinding.zola, layoutHeight - zolaHeight)
                    })
                } ?: run {
                    Log.d("PlayActivity", "[setFragmentReturn] The rewarded ad wasn't ready yet.")
                    Toast.makeText(playContext, "광고가 준비되지 않았습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun setRewardedAdCallback(){
        mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d("PlayActivity", "Ad was clicked.")
            }
            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d("PlayActivity", "Ad dismissed fullscreen content.")
                mRewardedAd = null
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Log.e("PlayActivity", "Ad failed to show fullscreen content.")
                mRewardedAd = null
            }
            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d("PlayActivity", "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d("PlayActivity", "Ad showed fullscreen content.")
            }
        }
    }

    fun onRestartAfterAd(){

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

    private fun loadRewardAd() {
        var adRequest = AdRequest.Builder().build()
        RewardedAd.load(this, resources.getString(R.string.reward_ad_id),
            adRequest, object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d("PlayActivity", "[loadRewardAd] Ad was loaded")
                    mRewardedAd = ad
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("PlayActivity", "[loadRewardAd] adError = [$adError]")
                    mRewardedAd = null
                }
            })
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