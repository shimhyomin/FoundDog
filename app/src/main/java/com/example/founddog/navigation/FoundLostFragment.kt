package com.example.founddog.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.founddog.MainActivity
import com.example.founddog.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_found_lost.*

class FoundLostFragment : Fragment(R.layout.fragment_found_lost) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        childFragmentManager.beginTransaction().add(frame_found_lost.id, FoundFragment()).commit()
        if(radio_lost.isChecked){
            replaceFragment(FoundFragment())
        }else{
            replaceFragment(LostFragment())
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_found_lost, container, false)
    }

    private fun replaceFragment(fragment: Fragment){
        Log.d(MainActivity.TAG, "fragment "+fragment)
        childFragmentManager.beginTransaction().replace(frame_found_lost.id, fragment).commit()
    }
}