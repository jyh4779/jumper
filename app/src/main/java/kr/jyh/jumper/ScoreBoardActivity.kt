package kr.jyh.jumper

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.jyh.jumper.Room.JumpRoomDatabase
import kr.jyh.jumper.Room.JumpRoomEntity
import kr.jyh.jumper.databinding.ActivityScoreboardBinding

class ScoreBoardActivity: AppCompatActivity() {
    private var db: JumpRoomDatabase? = null
    private var scoreboardBinding: ActivityScoreboardBinding? = null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        Log.d("ScoreBoardActivity", "[onCreate] Start")

        scoreboardBinding = DataBindingUtil.setContentView(this,R.layout.activity_scoreboard)

        db = JumpRoomDatabase.getInstance(this)
    }

    override fun onStart() {
        super.onStart()

        Log.d("ScoreBoardActivity", "[onStart] Start")

        getScoreList()
    }

    fun getScoreList() {
        var getdata: Array<JumpRoomEntity>?
        CoroutineScope(Dispatchers.IO).launch {
            getdata = db!!.JumpRoomDao().getScore()
            scoreboardBinding!!.scoreData = ScoreData(
                getdata!![0].sPlayerName, getdata!![0].score.toString(), getdata!![0].playDate,
                getdata!![1].sPlayerName, getdata!![1].score.toString(), getdata!![1].playDate,
                getdata!![2].sPlayerName, getdata!![2].score.toString(), getdata!![2].playDate,
                getdata!![3].sPlayerName, getdata!![3].score.toString(), getdata!![3].playDate,
                getdata!![4].sPlayerName, getdata!![4].score.toString(), getdata!![4].playDate,
                getdata!![5].sPlayerName, getdata!![5].score.toString(), getdata!![5].playDate,
                getdata!![6].sPlayerName, getdata!![6].score.toString(), getdata!![6].playDate,
                getdata!![7].sPlayerName, getdata!![7].score.toString(), getdata!![7].playDate,
                getdata!![8].sPlayerName, getdata!![8].score.toString(), getdata!![8].playDate,
                getdata!![9].sPlayerName, getdata!![9].score.toString(), getdata!![9].playDate)
        }
    }
}