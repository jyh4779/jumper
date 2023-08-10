package kr.jyh.jumper

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WallClass:AppCompatActivity() {
    fun wallCoroutine(){
        setInitWallMake()
        wallJob=CoroutineScope(Dispatchers.Main).launch {
            Log.d("WallClass", "[wallCoroutine] CoroutineScope Start!!")
            while(true){
                if(LIFECYCLE == LIFECYCLE_PAUSE) {
                    delay(DELAY)
                    continue
                }
                if(dZolaState == ZOLADEATH) break

                setWallGravity(makeWall(0F))

                delay(WALLDELAY)
            }
            Log.d("WallClass", "[wallCoroutine] CoroutineScope End!!")
        }
    }

    fun setWallGravity(wall:ImageView){
        CoroutineScope(Dispatchers.Main).launch {
            //Log.d("WallClass", "[setWallGravity] CoroutineScope Start!!")
            while(true){
                if(LIFECYCLE == LIFECYCLE_PAUSE) {
                    delay(DELAY)
                    continue
                }
                if(wall.getY() > layoutHeight){
                    playBinding.playLayout.removeView(wall)
                    break
                }
                wall.setY(wall.getY()+WALL_DOWN_SPEED)
                if(dZolaState == ZOLADROP) chkZolaOnWall(wall, playBinding.zola)
                if(dZolaState == ZOLADEATH) break
                delay(DELAY)
            }
        }
    }

    fun setInitWallMake(){
        for(i in 1..5){
            setWallGravity(makeWall(i*200F))
        }
    }

    fun makeWall(wallY:Float):ImageView {
        //Log.d("WallClass", "[makeWall] makeWall Start!!")
        var wallView = ImageView(playContext)

        var wallWidth = getRandomValue(WALLWIDTHMIN.toInt(), WALLWIDTHMAX.toInt())
        var wallX = getRandomValue(0, (layoutWidth-wallWidth).toInt()).toFloat()

        //Log.d("WallClass", "[makeWall] wallWidth [$wallWidth]")
        //Log.d("WallClass", "[makeWall] wallX [$wallX]")


        wallView.setBackgroundColor(Color.GRAY)
        setSize(wallView, wallWidth, WALLHEIGHT)
        wallView.setX(wallX)
        wallView.setY(wallY)
        wallView.setVisibility(View.VISIBLE)

        playBinding.playLayout.addView(wallView)

        return wallView
    }

    fun getRandomValue(start:Int, end:Int):Int{
        require(start <= end) { "Illegal Argument" }
        return (start..end).random()
    }

    fun setSize(v:View, width:Int, height:Int){
        val param: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        param.height = height
        param.width = width

        v.layoutParams = param
    }

    fun chkZolaOnWall(wall:View, zola:View){
        if(zola.getY()+zolaHeight >= wall.getY() && zola.getY()+zolaHeight <= wall.getY()+10) {
            if (wall.getX() > zola.getX() + zolaWidth) return
            if (wall.getX()+wall.getWidth() < zola.getX()) return
            dZolaState = ZOLASTAY
        }
    }
}