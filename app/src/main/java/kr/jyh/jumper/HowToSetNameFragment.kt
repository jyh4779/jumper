package kr.jyh.jumper

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.jyh.jumper.databinding.FragmentHowToSetNameBinding

class HowToSetNameFragment : Fragment() {
lateinit var binding:FragmentHowToSetNameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("HowToClass", "[onCreate] Start")
        binding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_how_to_set_name, container, false)

        swapGuideImage()

        // Inflate the layout for this fragment
        return binding.root
    }
    private fun swapGuideImage(){
        var imageFlag = 0
        CoroutineScope(Dispatchers.Main).launch {
            while(true){
                if (imageFlag == 0) {
                    binding.startImage.setImageResource(R.drawable.start2)
                    imageFlag = 1
                }
                else if (imageFlag == 1) {
                    binding.startImage.setImageResource(R.drawable.start)
                    imageFlag = 0
                }
                delay(500)
            }
        }
    }
}