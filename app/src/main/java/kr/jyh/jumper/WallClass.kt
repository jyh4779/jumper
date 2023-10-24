package kr.jyh.jumper

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.jyh.jumper.databinding.ActivityPlayBinding

class WallClass:AppCompatActivity() {
    fun wallCoroutine(playBinding: ActivityPlayBinding){
        setInitWallMake(playBinding)
        wallJob =CoroutineScope(Dispatchers.Main).launch {
            Log.d("WallClass", "[wallCoroutine] CoroutineScope Start!!")
            while(true){
                if(LIFECYCLE == LIFECYCLE_PAUSE) {
                    delay(DELAY)
                    continue
                }
                if(dZolaState == ZOLADEATH) break

                setWallGravity(makeWall(0F,playBinding),playBinding)

                delay(WALLDELAY)
            }
            Log.d("WallClass", "[wallCoroutine] CoroutineScope End!!")
        }
    }

    fun setWallGravity(wall:ImageView, playBinding: ActivityPlayBinding){
        CoroutineScope(Dispatchers.Main).launch {
            //Log.d("WallClass", "[setWallGravity] CoroutineScope Start!!")
            while(true){
                if(LIFECYCLE == LIFECYCLE_PAUSE) {
                    delay(DELAY)
                    continue
                }
                if(wall.getY() > layoutHeight){
                    playBinding.playLayout.removeView(wall)
                    WALL_DOWN_SPEED = 10
                    remainWallCnt--
                    Log.d("WallClass","[makeWall] RemainWall[$remainWallCnt]")
                    break
                }
                wall.setY(wall.getY()+ WALL_DOWN_SPEED)
                if(dZolaState == ZOLADROP) chkZolaOnWall(wall, playBinding.zola)
                if(dZolaState == ZOLADEATH) break
                delay(DELAY)
            }
        }
    }

    fun setInitWallMake(playBinding: ActivityPlayBinding){
        for(i in 1..5){
            setWallGravity(makeWall(i*200F,playBinding),playBinding)
        }
    }

    fun makeWall(wallY:Float,playBinding: ActivityPlayBinding):ImageView {
        //Log.d("WallClass", "[makeWall] makeWall Start!!")
        var wallView = ImageView(playContext)

        var wallWidth = getRandomValue(WALLWIDTHMIN.toInt(), WALLWIDTHMAX.toInt())
        var wallX = getRandomValue(0, (layoutWidth -wallWidth).toInt()).toFloat()

        //Log.d("WallClass", "[makeWall] wallWidth [$wallWidth]")
        //Log.d("WallClass", "[makeWall] wallX [$wallX]")


        wallView.setBackgroundColor(Color.GRAY)
        setSize(wallView, wallWidth, WALLHEIGHT)
        wallView.setX(wallX)
        wallView.setY(wallY)
        wallView.setVisibility(View.VISIBLE)
        //wallView.id = ViewCompat.generateViewId()
        //lastWallId = wallView.getId()
        //Log.d("WallClass","[makeWall] ID[$lastWallId] wallX[$wallX] Width[$wallWidth]")

        playBinding.playLayout.addView(wallView)

        /*remainWallCnt++
        Log.d("WallClass","[makeWall] RemainWall[$remainWallCnt]")*/

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
        /*var id = wall.getId()
        if(id < (lastWallId- remainWallCnt)) return*/
        if(zola.getY()+ zolaHeight >= wall.getY() && zola.getY()+ zolaHeight <= wall.getY()+10) {
            if (wall.getX() > zola.getX() + zolaWidth) return
            if (wall.getX()+wall.getWidth() < zola.getX()) return
            dZolaState = ZOLASTAY
        /*
            var a = zola.getX()
            var a2 = a+ zolaWidth
            var b = wall.getX()
            var b2 = b+wall.getWidth()
            var aa = zola.getY()
            var aa2 = aa+ zolaHeight
            var bb = wall.getY()
            var bb2 = bb+wall.getHeight()
            Log.d("WallClass","[chkZolaOnWall] zolaX[$a], zolaX2[$a2]")
            Log.d("WallClass","[chkZolaOnWall] wallX[$b], wallX2[$b2]")
            Log.d("WallClass","[chkZolaOnWall] zolaY[$aa], zolaY2[$aa2]")
            Log.d("WallClass","[chkZolaOnWall] wallY[$bb], wallY2[$bb2]")
            Log.d("WallClass","[chkZolaOnWall] ID[$id] wallWidth["+wall.getWidth()+"]")*/

        }
    }

    /*fun removeRemainAllWall(){
        var wallId = lastWallId
        while(remainWallCnt != 0){
            Log.d("WallClass","[removeRemainAllWall] RemainWall[$remainWallCnt], LastId[$wallId]")

            val wallView:ImageView = playBinding.playLayout.findViewById(wallId)

            playBinding.playLayout.removeView(wallView)
            wallId--
            remainWallCnt--
        }
    }*/
}