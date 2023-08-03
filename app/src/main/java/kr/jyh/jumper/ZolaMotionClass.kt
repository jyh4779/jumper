package kr.jyh.jumper

import android.util.Log
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ZolaMotionClass {
    fun setZolaJumpMotion(zola: ImageView){
        zola.setImageResource(R.drawable.seatzola)
    }

    fun setZolaDefaultMotion(zola: ImageView){
        zola.setImageResource(R.drawable.defaultzola)
    }

    fun getZolaAngle():Float{
        //Log.d("ZolaMotionClass", "[getJumpPower] fClickAngle[$fClickAngle]!!")
        if(dZolaState == ZOLAJUMP || dZolaState == ZOLADROP) {
            if(fClickAngle == 0F) return 0F
            else if(fClickAngle >= 40F) return 19F
            else if(fClickAngle <= -40F) return -19F
            else if(fClickAngle < 40F && fClickAngle >= 35F) return 17F
            else if(fClickAngle > -40F && fClickAngle <= -35F) return -17F
            else if(fClickAngle < 35F && fClickAngle >= 30F) return 15F
            else if(fClickAngle > -35F && fClickAngle <= -30F) return -15F
            else if(fClickAngle < 30F && fClickAngle >= 25F) return 13F
            else if(fClickAngle > -30F && fClickAngle <= -25F) return -13F
            else if(fClickAngle < 25F && fClickAngle >= 20F) return 11F
            else if(fClickAngle > -25F && fClickAngle <= -20F) return -11F
            else if(fClickAngle < 20F && fClickAngle >= 15F) return 9F
            else if(fClickAngle > -20F && fClickAngle <= -15F) return -9F
            else if(fClickAngle < 15F && fClickAngle >= 10F) return 7F
            else if(fClickAngle > -15F && fClickAngle <= -10F) return -7F
            else if(fClickAngle < 10F && fClickAngle >= 5F) return 5F
            else if(fClickAngle > -10F && fClickAngle <= -5F) return -5F
            else if(fClickAngle < 5F && fClickAngle >= 0F) return 3F
            else if(fClickAngle > -5F && fClickAngle <= -0F) return -3F
        }
        return 0F
    }

    fun getZolaPower():Float{
        //Log.d("ZolaMotionClass", "[getJumpPower] fClickPower[$fClickPower]!!")
        if(dZolaState == ZOLAJUMP) {
            if(fClickPower <= 0F) {
                fClickPower = 0F
                dZolaState = ZOLADROP
                return 0F
            }
            else if(fClickPower <= 10F && fClickPower > 0F){
                fClickPower = 0F
                dZolaState = ZOLADROP
                return 20F
            }
            else if(fClickPower <= 50F && fClickPower > 10F){
                fClickPower -= 20F
                return 30F
            }
            else {
                fClickPower -= 30F
                return 50F
            }
        }
        return 0F
    }

    fun setZolaXY(zola:ImageView, deathLine:Float){
        CoroutineScope(Dispatchers.Main).launch {
            Log.d("ZolaMotionClass", "[setZolaXY] CoroutineScope Start!!")
            while(true){
                Log.d("ZolaMotionClass", "[setZolaXY] ZOLASTATE[$dZolaState]")

                // 캐릭터 Y축 이동
                if(dZolaState == ZOLAJUMP || dZolaState == ZOLADROP) zola.setY(zola.getY() + GRAVITY_DOWN_SPEED - getZolaPower()) // 캐릭터 자유낙하
                if(dZolaState == ZOLASTAY) zola.setY(zola.getY() + WALL_DOWN_SPEED) // 캐릭터 벽 밟는 중

                // 캐릭터 X축 이동
                if(dZolaState == ZOLAJUMP || dZolaState == ZOLADROP) zola.setX(zola.getX()+getZolaAngle())

                // 캐릭터 Layout 끝 부딫힘
                if(zola.getX() <= 0F || zola.getX()+zolaWidth >= layoutWidth) fClickAngle = -fClickAngle

                // 캐릭터 바닥에 닿음
                if(zola.getY() > deathLine) if(dZolaState != ZOLASTART) break

                delay(DELAY)
            }
            dZolaState = ZOLADEATH
            Log.d("ZolaMotionClass", "[setZolaXY] CoroutineScope End!!")
        }
    }

    /*fun getZolaGravity(postY:Float):Float{
        if(dZolaState == ZOLASTAY) return postY+WALL_DOWN_SPEED
        return postY+GRAVITY_DOWN_SPEED
    }*/
}