package kr.jyh.jumper

import android.util.Log
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ZolaMotionClass {
    var dZolaState = 0
    fun setZolaPosition(zola:ImageView, power:Float, angle:Float){

    }
    fun setZolaJumpMotion(zola: ImageView){
        zola.setImageResource(R.drawable.seatzola)
    }

    fun setZolaDefaultMotion(zola: ImageView){
        zola.setImageResource(R.drawable.defaultzola)
    }

    fun setZolaInGravity(zola:ImageView, deathLine:Float){
        CoroutineScope(Dispatchers.Main).launch {
            Log.d("ZolaMotionClass", "[setZolaInGravity] CoroutineScope Start!!")

            while(true){
                if(dZolaState == 0){
                    var zolaY = zola.getY()
                    Log.d("ZolaMotionClass", "[setZolaInGravity] deathLine[$deathLine]")
                    Log.d("ZolaMotionClass", "[setZolaInGravity] zolaY[$zolaY]")

                    if(zolaY > deathLine){
                        Log.d("ZolaMotionClass", "[setZolaInGravity] zolaY is deathLine")
                        dZolaState = 2
                    }
                    zola.setY(zolaY+20)
                    delay(50)
                }
                else{
                    break
                }
            }
            Log.d("ZolaMotionClass", "[setZolaInGravity] CoroutineScope End!!")
        }
    }
}