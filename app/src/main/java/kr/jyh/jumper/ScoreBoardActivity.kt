package kr.jyh.jumper

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.jyh.jumper.Room.JumpRoomDatabase
import kr.jyh.jumper.Room.JumpRoomEntity
import kr.jyh.jumper.Socket.SELECT_WORLD_RECORD
import kr.jyh.jumper.Socket.SocketClientClass

class ScoreBoardActivity: AppCompatActivity() {
    //private var db: JumpRoomDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        Log.d("ScoreBoardActivity", "[onCreate] Start")

        scoreboardBinding = DataBindingUtil.setContentView(this,R.layout.activity_scoreboard)

        //db = JumpRoomDatabase.getInstance(this)

        scoreboardBinding.homeBtn.setOnClickListener{
            finish()
        }
        scoreboardBinding.testBtn.setOnClickListener{
            var test = SocketClientClass()
            test.selectWolrdRecord()
        }
        getScoreList()
    }

    fun getScoreList() {
        var socket = SocketClientClass()
        var scoreDataArray = arrayOf(arrayOf(" "," "," "),arrayOf(" "," "," "),arrayOf( " ", " ", " "),
            arrayOf( " ", " ", " "),arrayOf( " ", " ", " "),arrayOf( " ", " ", " "),arrayOf( " ", " ", " ")
        ,arrayOf( " ", " ", " "),arrayOf( " ", " ", " "),arrayOf( " ", " ", " "))
        CoroutineScope(Dispatchers.IO).async {
            scoreDataArray = socket.selectWolrdRecord()
        }
        while(SCOREBOARDDATADELAY == 0){
            GlobalScope.launch {
                delay(500)
            }
        }
        Log.d("ScoreBoardActivity", "[getScoreList] SCOREBOARDDATADELAY = $SCOREBOARDDATADELAY")

        SCOREBOARDDATADELAY = 0
        Log.d("ScoreBoardActivity", "[getScoreList] SCOREBOARDDATADELAY = $SCOREBOARDDATADELAY")

        for(i in 0..9){
            Log.d("ScoreBoardActivity", "[getScoreList] ScoreData[$i] = [${scoreDataArray[i][0]}]")
            Log.d("ScoreBoardActivity", "[getScoreList] ScoreData[$i] = [${scoreDataArray[i][1]}]")
            Log.d("ScoreBoardActivity", "[getScoreList] ScoreData[$i] = [${scoreDataArray[i][2]}]")
            setScoreData(i, scoreDataArray[i])
        }
    }

    fun setScoreData(index:Int, data:Array<String>) {
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
        name.setText(data[1])
        score.setText(data[2])
        time.setText(data[0])
    }
}