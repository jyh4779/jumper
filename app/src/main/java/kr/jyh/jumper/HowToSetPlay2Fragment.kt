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
import kr.jyh.jumper.databinding.FragmentHowToSetPlay2Binding

class HowToSetPlay2Fragment : Fragment() {

    lateinit var binding: FragmentHowToSetPlay2Binding
    lateinit var howToActivity: HowToActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("HowToClass", "[onCreate] Start")
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_how_to_set_play2, container, false
        )

        howToActivity = activity as HowToActivity

        swapGuideImage()

        binding.beforepage.setOnClickListener{
            howToActivity.changeFrament("PLAY")
        }
        binding.nextpage.setOnClickListener{
            howToActivity.changeFrament("DEATH")
        }

        // Inflate the layout for this fragment
        return binding.root
    }
    private fun swapGuideImage(){
        var imageFlag = 0
        CoroutineScope(Dispatchers.Main).launch {
            while(true){
                if (imageFlag == 0) {
                    binding.startImage.setImageResource(R.drawable.afterjump2)
                    imageFlag = 1
                }
                else if (imageFlag == 1) {
                    binding.startImage.setImageResource(R.drawable.afterjump3)
                    imageFlag = 2
                }
                else if (imageFlag == 2) {
                    binding.startImage.setImageResource(R.drawable.afterjump)
                    imageFlag = 0
                }
                delay(500)
            }
        }
    }
}