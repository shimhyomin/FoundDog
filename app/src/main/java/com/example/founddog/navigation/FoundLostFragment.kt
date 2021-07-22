package com.example.founddog.navigation

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.FragmentActivity
import com.example.founddog.MainActivity
import com.example.founddog.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_found_lost.*
import java.lang.Exception

class FoundLostFragment : Fragment() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var radioLost: RadioButton
    private lateinit var radioFound: RadioButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioGroup = view.findViewById(R.id.radiogroup_found_lost)
        radioLost = view.findViewById(R.id.radio_lost)
        radioFound = view.findViewById(R.id.radio_found)

        try {
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_lost -> {
                        text_view.setText("Lost")
                    }
                    R.id.radio_found -> {
                        text_view.setText("Found")
                    }
                }
            }
        } catch (e : Exception){
            Log.d("FoundLost", "Error :" + e)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_found_lost, container, false)

//        childFragmentManager.beginTransaction().add(frame_found_lost.id, FoundFragment()).commit()
//        if(radio_lost.isChecked){
//            replaceFragment(FoundFragment())
//        }else{
//            replaceFragment(LostFragment())
//        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_found_lost, container, false)


    }


//    private fun replaceFragment(fragment: Fragment){
//        Log.d(MainActivity.TAG, "fragment "+fragment)
//        childFragmentManager.beginTransaction().replace(frame_found_lost.id, fragment).commit()
//    }
}