package kr.jyh.jumper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kr.jyh.jumper.Room.JumpRoomDatabase
import kr.jyh.jumper.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var mainBinding: ActivityMainBinding

    private var db: JumpRoomDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("MainActivity", "onCreate Start")

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        db = JumpRoomDatabase.getInstance(this)

        getLastPlayerName()

        mainBinding.startBtn.setOnClickListener {
            playerName = mainBinding.scoreET.text!!.toString()
            startActivity(Intent(this,PlayActivity::class.java))
        }
        mainBinding.scoreBtn.setOnClickListener {
            startActivity(Intent(this,ScoreBoardActivity::class.java))
        }
        mainBinding.howToBtn.setOnClickListener {
            startActivity(Intent(this,HowToActivity::class.java))
        }
    }


    fun getLastPlayerName() {
        CoroutineScope(Dispatchers.IO).launch {
            val name = async { selectData() }
            mainBinding.scoreET.setText(name.await())
        }
    }
    fun selectData():String? {
        var name:String? = null

        name = db!!.JumpRoomDao().getName()
        Log.d("MainActivity","[selectData] insert DB Data name[$name]" )

        return name?:"no name"
    }
}
