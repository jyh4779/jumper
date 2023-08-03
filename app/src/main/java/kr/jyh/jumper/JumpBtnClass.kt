package kr.jyh.jumper

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import java.lang.Math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

class JumpBtnClass {
    var startX = 0F
    var startY = 0F
    var touchPointX = 0F
    var touchPointY = 0F

    fun downTouchPoint(tPoint:View, tLine:View){
        //Log.d("JumpBtnClass","[downTouchPoint] start")

        //Log.d("JumpBtnClass","[downTouchPoint] TouchPointX = $touchPointX")
        //Log.d("JumpBtnClass","[downTouchPoint] TouchPointY = $touchPointY")
        if(dZolaState==ZOLAJUMP || dZolaState==ZOLADEATH || dZolaState== ZOLADROP ) return

        tPoint.setX(touchPointX)
        tPoint.setY(touchPointY)
        tPoint.setVisibility(View.VISIBLE)

        var tLineY = startY - tLine.getHeight()/2

        //Log.d("JumpBtnClass","[downJumpBtn] TouchLineX = $startX")
        //Log.d("JumpBtnClass","[downJumpBtn] TouchLineY = $tLineY")

        tLine.setX(startX)
        tLine.setY(tLineY)
        tLine.setVisibility(View.VISIBLE)
    }
    fun moveTouchPoint(endX:Float, endY:Float, tLine:View):String {
        //Log.d("JumpBtnClass","[moveTouchPoint] start")

        if(dZolaState==ZOLAJUMP || dZolaState==ZOLADEATH || dZolaState== ZOLADROP ) return "DEATH"

        var tLineAngle = getDegree(endX, endY).toFloat()
        if(tLineAngle > 45) tLineAngle = 45F
        if(tLineAngle < -45) tLineAngle = -45F

        tLine.rotation = tLineAngle

        if ((getLineHeight(endX, endY).toInt())*2 > MAXPOWERGAUGE) setSize(tLine, MAXPOWERGAUGE)
        else setSize(tLine, getLineHeight(endX, endY).toInt()*2)

        var tLineY = startY - tLine.getHeight()/2
        tLine.setY(tLineY)

        return tLine.rotation.toString()
    }
    fun upTouchPoint(tPoint:View, tLine:View, x:Float, y:Float){
        //Log.d("JumpBtnClass","[upTouchPoint] start")

        //Log.d("JumpBtnClass","[upTouchPoint] x = $x")
        //Log.d("JumpBtnClass","[upTouchPoint] y = $y")

        if(dZolaState==ZOLAJUMP || dZolaState==ZOLADEATH || dZolaState== ZOLADROP ) return

        fClickPower = tLine.getHeight().toFloat()
        fClickAngle = tLine.rotation

        tPoint.setVisibility(View.INVISIBLE)
        tLine.setVisibility(View.INVISIBLE)

        dZolaState = ZOLAJUMP
    }

    fun getDegree(endX:Float, endY:Float): Double {
        val dX = endX - startX
        val dY = endY - startY

        return -Math.toDegrees(atan2(dX.toDouble(), dY.toDouble()))
    }

    fun getLineHeight(endX:Float, endY:Float):Float{
        //Log.d("JumpBtnClass", "[getLineHeight] Start")

        var powWidth = (endX - startX).pow(2)
        var powHeight = (endY - startY).pow(2)

        //Log.d("JumpBtnClass", "[getLineHeight] LineHeight = ["+sqrt(powWidth+powHeight)+"]")

        return (sqrt(powWidth+powHeight))
    }

    fun setSize(v: View, height:Int){
        val param: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT)
        param.height = height

        v.layoutParams = param
    }
}