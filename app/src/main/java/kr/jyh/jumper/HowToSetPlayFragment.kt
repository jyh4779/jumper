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
import kr.jyh.jumper.databinding.FragmentHowToSetPlayBinding

class HowToSetPlayFragment : Fragment() {

    lateinit var binding: FragmentHowToSetPlayBinding
    lateinit var howToActivity: HowToActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("HowToClass", "[onCreate] Start")
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_how_to_set_play, container, false
        )

        howToActivity = activity as HowToActivity

        swapGuideImage()

        binding.beforepage.setOnClickListener{
            howToActivity.changeFrament("NAME")
        }
        binding.nextpage.setOnClickListener{
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
                    binding.startImage.setImageResource(R.drawable.beforejump2)
                    imageFlag = 1
                }
                else if (imageFlag == 1) {
                    binding.startImage.setImageResource(R.drawable.beforejump)
                    imageFlag = 0
                }
                delay(500)
            }
        }
    }
}