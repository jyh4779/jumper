package kr.jyh.jumper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.jyh.jumper.Shared.PreferenceUtil
import kr.jyh.jumper.Socket.SocketClientClass

class StartActivity : AppCompatActivity() {
    var isLogin = 0;
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try{
            if (result.resultCode == RESULT_OK ) {
                Log.d("StartActivity","RESULTCODE = [${result.resultCode}]")
                val account = task.getResult(ApiException::class.java)
                val userName = account.givenName
                val userMail = account.email
                firebaseAuthWithGoogle(account.idToken!!)
                Log.d("StartActivity","account name[$userName], email[$userMail]")
            }
            else if (result.resultCode == RESULT_CANCELED){
                Log.d("StartActivity","RESULTCODE = [${result.resultCode}]")
            }
            else if (result.resultCode == RESULT_FIRST_USER){
                Log.d("StartActivity","RESULTCODE = [${result.resultCode}]")
            }
        } catch(e: ApiException){
            Log.e("StartActivity", e.stackTraceToString())
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("StartActivity", "[onCreate] Start")

        startBinding = DataBindingUtil.setContentView(this,R.layout.activity_start)

        prer = PreferenceUtil(applicationContext)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_login_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth

        startBinding.signBtn.setOnClickListener{
            signIn()
        }

        /*startBinding.updateBtn.setOnClickListener {
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
        }*/
    }
   override fun onStart(){
        super.onStart()
       Log.d("StartActivity","[onStart] Start!!!")
       val currentUser = auth.currentUser
       Log.d("StartActivity","[onStart] currentUser[$currentUser]")
       if(currentUser != null) updateUI(currentUser)
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("StartActivity", "[onActivityResult] data = [$data]")

        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("StartActivity", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            }catch (e:ApiException){
                Log.w("StartActivity", "Google sign in failed", e)
            }
        }
        else Log.d("StartActivity", "requestCode = [$requestCode]")
    }*/
    private fun firebaseAuthWithGoogle(idToken:String){
        Log.d("StartActivity", "[firebaseAuthWithGoogle] idToken = [$idToken]")

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful) {
                    Log.d("StartActivity", "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w("StartActivity", "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }
    private fun signIn() {
        Log.d("StartActivity", "[signIn] Start!!")

        //googleSignInClient.signOut()

        val signInIntent = googleSignInClient.signInIntent
        Log.d("StartActivity", "[signIn] signInIntent[$signInIntent]")

        googleAuthLauncher.launch(signInIntent)
    }
    private fun updateUI(user: FirebaseUser?) {
        if(user == null) {
            Log.d("StartActivity","[updateUI] user is null")

            prer.setString("EMAIL","OFFLINE")
            Toast.makeText(this,"로그인 실패. 오프라인 모드로 실행됩니다.",Toast.LENGTH_LONG)
            Log.d("StartActivity","[updateUI] user EMAIL = ${prer.getString("EMAIL","")}")
        }
        else {
            Log.d("StartActivity","[updateUI] user is [$user]")
            var email = user.email.toString().split("@")
            prer.setString("EMAIL",email[0])
            prer.setString("NAME",user.displayName.toString())
            Log.d("StartActivity","[updateUI] user EMAIL = ${prer.getString("EMAIL","")}")
            Log.d("StartActivity","[updateUI] user NAME = ${prer.getString("NAME","")}")

            var test = SocketClientClass()
            test.loginToServer(prer.getString("EMAIL",""),prer.getString("NAME",""))
        }
        startMainActivity()
    }

    fun startMainActivity() {
        Log.d("StartActivity","[startMainActivity] Start MainActivity [${prer.getString("EMAIL","")}][${prer.getString("NAME","")}]")

        val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}