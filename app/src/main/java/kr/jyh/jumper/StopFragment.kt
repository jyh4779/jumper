package kr.jyh.jumper

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import kr.jyh.jumper.Room.JumpRoomDatabase
import kr.jyh.jumper.databinding.FragmentStopBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class StopFragment : Fragment(), View.OnClickListener {

    private var db: JumpRoomDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentBinding = FragmentStopBinding.inflate(inflater, container, false)

        db = JumpRoomDatabase.getInstance(playContext)

        var playerName = db!!.JumpRoomDao().getName()

        fragmentBinding.fragmentData = FragmentData(fragmentData, score.toString(), playerName)

        setBtnEventListener()

        return fragmentBinding.root
    }

    fun setBtnEventListener(){
        fragmentBinding.okBtn.setOnClickListener(this)
        fragmentBinding.cancelBtn.setOnClickListener(this)
        playBinding.playLayout.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v:View){
        val pActivity = activity as PlayActivity
        when(v.id){
            R.id.okBtn -> {
                val curDate = getDate()
                Log.d("StopFragment","[onClick] getDate = [$curDate]")
//                db!!.JumpRoomDao().insertScore()
                pActivity.setFragmentReturn("OK")
            }
            R.id.cancelBtn -> {
                pActivity.setFragmentReturn("CANCEL")
            }
            R.id.playLayout -> {
                pActivity.setFragmentReturn("RESTART")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDate():String {
        val curDate = LocalDateTime.now()
        return curDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm:ss"))
    }
}