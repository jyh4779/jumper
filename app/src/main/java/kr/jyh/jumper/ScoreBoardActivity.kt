package kr.jyh.jumper

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.jyh.jumper.Room.JumpRoomDatabase
import kr.jyh.jumper.Room.JumpRoomEntity
import kr.jyh.jumper.Socket.SELECT_WORLD_RECORD
import kr.jyh.jumper.Socket.SocketClientClass

class ScoreBoardActivity: AppCompatActivity() {
    private var db: JumpRoomDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        Log.d("ScoreBoardActivity", "[onCreate] Start")

        scoreboardBinding = DataBindingUtil.setContentView(this,R.layout.activity_scoreboard)

        db = JumpRoomDatabase.getInstance(this)

        scoreboardBinding.homeBtn.setOnClickListener{
            finish()
        }
        scoreboardBinding.testBtn.setOnClickListener{
            var test = SocketClientClass()
            test.selectWolrdRecord()
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d("ScoreBoardActivity", "[onStart] Start")

        getScoreList()
    }

    fun getScoreList() {
        var getdata: Array<JumpRoomEntity>

        CoroutineScope(Dispatchers.IO).launch {
            getdata = db!!.JumpRoomDao().getScore()
            for(i in 0..getdata.size-1){
                    setScoreData(getdata[i], i)
            }
        }
    }

    fun setScoreData(arr:JumpRoomEntity, index:Int) {
        lateinit var name: TextView
        lateinit var score: TextView
        lateinit var time: TextView
        when (index) {
            0 -> {
                name = scoreboardBinding.name1
                score = scoreboardBinding.score1
                time = scoreboardBinding.time1
            }

            1 -> {
                name = scoreboardBinding.name2
                score = scoreboardBinding.score2
                time = scoreboardBinding.time2
            }

            2 -> {
                name = scoreboardBinding.name3
                score = scoreboardBinding.score3
                time = scoreboardBinding.time3
            }

            3 -> {
                name = scoreboardBinding.name4
                score = scoreboardBinding.score4
                time = scoreboardBinding.time4
            }

            4 -> {
                name = scoreboardBinding.name5
                score = scoreboardBinding.score5
                time = scoreboardBinding.time5
            }

            5 -> {
                name = scoreboardBinding.name6
                score = scoreboardBinding.score6
                time = scoreboardBinding.time6
            }

            6 -> {
                name = scoreboardBinding.name7
                score = scoreboardBinding.score7
                time = scoreboardBinding.time7
            }

            7 -> {
                name = scoreboardBinding.name8
                score = scoreboardBinding.score8
                time = scoreboardBinding.time8
            }

            8 -> {
                name = scoreboardBinding.name9
                score = scoreboardBinding.score9
                time = scoreboardBinding.time9
            }

            9 -> {
                name = scoreboardBinding.name10
                score = scoreboardBinding.score10
                time = scoreboardBinding.time10
            }
        }
        name.setText(arr.sPlayerName)
        score.setText(arr.score.toString())
        time.setText(arr.playDate)
    }
}