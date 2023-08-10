package kr.jyh.jumper

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("StartActivity", "[onCreate] Start")

        startBinding = DataBindingUtil.setContentView(this,R.layout.activity_start)

        startBinding.updateBtn.setOnClickListener {
            val updateIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=kr.jyh.jumper&hl=en-US&ah=FCSk0q07NZNsREPtF2cEiwBAue4"))
            startActivity(updateIntent)
        }
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if(appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                Log.d("if","start")
                startBinding.updateLayout.setVisibility(View.VISIBLE)
            } else {
                Log.d("else","start")
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                },500)
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },500)


    }
}