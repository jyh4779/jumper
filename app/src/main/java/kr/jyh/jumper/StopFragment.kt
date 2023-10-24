package kr.jyh.jumper

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.jyh.jumper.Room.JumpRoomDatabase
import kr.jyh.jumper.Room.JumpRoomEntity
import kr.jyh.jumper.Shared.PreferenceUtil
import kr.jyh.jumper.Socket.SocketClientClass
import kr.jyh.jumper.databinding.FragmentStopBinding
import kr.jyh.jumper.fragmentData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StopFragment : Fragment(), View.OnClickListener {

    private var db: JumpRoomDatabase? = null
    private lateinit var pActivity: PlayActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentBinding = FragmentStopBinding.inflate(inflater, container, false)

        pActivity = activity as PlayActivity

        db = JumpRoomDatabase.getInstance(playContext)

        //fragmentBinding.fragmentData = FragmentData(fragmentData, score.toString(), playerName)
        fragmentBinding.fragmentData = FragmentData(fragmentData, "다시하기", score.toString())

        setBtnEventListener()

        return fragmentBinding.root
    }

    fun setBtnEventListener(){
        fragmentBinding.okBtn.setOnClickListener(this)
        fragmentBinding.cancelBtn.setOnClickListener(this)
        pActivity.playBinding.playLayout.setOnClickListener(this)
    }

    override fun onClick(v:View){
        val pActivity = activity as PlayActivity
        when(v.id){
            R.id.okBtn -> {
                saveDBData()
                pActivity.setFragmentReturn("OK")
            }
            R.id.cancelBtn -> {
                if(rewardAdFlag == 0) pActivity.setFragmentReturn("ADSTART")
                else pActivity.setFragmentReturn("CANCEL")
            }
            R.id.playLayout -> {
                pActivity.setFragmentReturn("RESTART")
            }
        }
    }

    /*@RequiresApi(Build.VERSION_CODES.O)
    fun getDate():String {
        val curDate = LocalDateTime.now()
        return curDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm:ss"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveDBData() {
        CoroutineScope(Dispatchers.IO).launch {
            db!!.JumpRoomDao().insertScore(JumpRoomEntity(getDate(), playerName ?:"default", score))
        }
    }*/

    fun saveDBData() {
        var socket = SocketClientClass()
        Log.d("StopFragment","[saveDBData] Start!!!")
        Log.d("StopFragment","[saveDBData] [${prer.getString("EMAIL","")}] score[$score]!!!")
        socket.saveScoreToServer(prer.getString("EMAIL",""),score)

    }
}