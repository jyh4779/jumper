package kr.jyh.jumper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.jyh.jumper.databinding.FragmentHowTowSetDeathBinding

class HowToSetDeathFragment : Fragment() {

    lateinit var binding: FragmentHowTowSetDeathBinding
    lateinit var howToActivity: HowToActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("HowToClass", "[onCreate] Start")
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_how_tow_set_death, container, false
        )

        howToActivity = activity as HowToActivity

        swapGuideImage()

        binding.beforepage.setOnClickListener{
            howToActivity.changeFrament("PLAY2")
        }

        // Inflate the layout for this fragment
        return binding.root
    }
    private fun swapGuideImage(){
        var imageFlag = 0
        CoroutineScope(Dispatchers.Main).launch {
            while(true){
                if (imageFlag == 0) {
                    binding.startImage.setImageResource(R.drawable.death2)
                    imageFlag = 1
                }
                else if (imageFlag == 1) {
                    binding.startImage.setImageResource(R.drawable.death)
                    imageFlag = 0
                }
                delay(500)
            }
        }
    }
}