package kr.jyh.jumper

import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel

class JumpViewModel : ViewModel(), View.OnTouchListener {
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                Toast.makeText(v.context, "[Touch] ACTION_DOWN", Toast.LENGTH_SHORT).show()
            }
            MotionEvent.ACTION_MOVE -> {
                Toast.makeText(v.context, "[Touch] ACTION_MOVE", Toast.LENGTH_SHORT).show()
            }
            MotionEvent.ACTION_UP -> {
                Toast.makeText(v.context, "[Touch] ACTION_UP", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}