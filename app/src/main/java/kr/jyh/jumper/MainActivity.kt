package kr.jyh.jumper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import kr.jyh.jumper.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("MainActivity", "onCreate Start")

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val mainData = MainData("Start", 50)

        mainBinding.mainData = mainData

        mainBinding.startBtn.setOnClickListener {
            startActivity(Intent(this,PlayActivity::class.java))
        }
    }
}
