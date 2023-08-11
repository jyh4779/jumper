package kr.jyh.jumper

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kr.jyh.jumper.databinding.ActivityHowtoBinding

private const val TAG_NAME_FRAGMENT="how_to_set_name"
private const val TAG_PLAY_FRAGMENT="how_to_set_play"
private const val TAG_PLAY2_FRAGMENT="how_to_set_play2"
private const val TAG_DEATH_FRAGMENT="how_to_set_death"
class HowToActivity: FragmentActivity() {
    lateinit var binding: ActivityHowtoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("HowToClass", "[onCreate] Start")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_howto)

        setFragment(TAG_NAME_FRAGMENT, HowToSetNameFragment())

        /*val transaction = supportFragmentManager
            .beginTransaction()
            .add(R.id.howtoFrameLayout,HowToSetNameFragment())
        transaction.commit()*/

        binding.homeBtn.setOnClickListener {
            finish()
        }
        binding
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null) {
            ft.add(R.id.howtoFrameLayout, fragment, tag)
        }

        val name = manager.findFragmentByTag(TAG_NAME_FRAGMENT)
        val play = manager.findFragmentByTag(TAG_PLAY_FRAGMENT)
        val play2 = manager.findFragmentByTag(TAG_PLAY2_FRAGMENT)
        val death = manager.findFragmentByTag(TAG_DEATH_FRAGMENT)

        // Hide all Fragment
        if (name != null) {ft.hide(name)}
        if (play != null) {ft.hide(play)}
        if (play2 != null) {ft.hide(play2)}
        if (death != null) {ft.hide(death)}

        // Show  current Fragment
        when (tag) {
            TAG_NAME_FRAGMENT -> {if (name != null) {ft.show(name)}}
            TAG_PLAY_FRAGMENT -> {if (play != null) {ft.show(play)}}
            TAG_PLAY2_FRAGMENT -> {if (play2 != null) {ft.show(play2)}}
            TAG_DEATH_FRAGMENT -> {if (death != null) {ft.show(death)}}
        }
        /*if (tag == TAG_HOME_FRAGMENT) {if (home != null) {ft.show(home)}}
        if (tag == TAG_BALTAN_FRAGMENT) {if (baltan != null) {ft.show(baltan)}}*/

        ft.commitAllowingStateLoss()
    }

    fun changeFrament (raid: String){
        when(raid){
            "NAME" -> {setFragment(TAG_NAME_FRAGMENT, HowToSetNameFragment())}
            "PLAY" -> {setFragment(TAG_PLAY_FRAGMENT, HowToSetPlayFragment())}
            "PLAY2" -> {setFragment(TAG_PLAY2_FRAGMENT, HowToSetPlay2Fragment())}
            "DEATH" -> {setFragment(TAG_DEATH_FRAGMENT, HowToSetDeathFragment())}
        }
    }
}