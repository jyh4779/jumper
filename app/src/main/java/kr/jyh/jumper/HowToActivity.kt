package kr.jyh.jumper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import kr.jyh.jumper.databinding.ActivityHowtoBinding

class HowToActivity: FragmentActivity() {
    lateinit var binding:ActivityHowtoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("HowToClass", "[onCreate] Start")
        binding = DataBindingUtil.setContentView(this,R.layout.activity_howto)

        val transaction = supportFragmentManager
            .beginTransaction()
            .add(R.id.howtoFrameLayout,HowToSetNameFragment())
        transaction.commit()

        binding.homeBtn.setOnClickListener {
            finish()
        }
    }
}