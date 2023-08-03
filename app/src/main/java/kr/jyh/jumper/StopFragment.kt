package kr.jyh.jumper

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kr.jyh.jumper.databinding.FragmentStopBinding

class StopFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentBinding = FragmentStopBinding.inflate(inflater, container, false)

        fragmentBinding.fragmentData = FragmentData(fragmentData, score.toString())

        setBtnEventListener()

        return fragmentBinding.root
    }

    fun setBtnEventListener(){
        fragmentBinding.okBtn.setOnClickListener(this)
        fragmentBinding.cancelBtn.setOnClickListener(this)
        playBinding.playLayout.setOnClickListener(this)
    }

    override fun onClick(v:View){
        val pActivity = activity as PlayActivity
        when(v.id){
            R.id.okBtn -> {
                pActivity.setFragmentReturn("OK")
            }
            R.id.cancelBtn -> {
                pActivity.setFragmentReturn("CANCEL")
            }
            R.id.playLayout -> {
                pActivity.setFragmentReturn("RESTART")
            }
        }
    }
}