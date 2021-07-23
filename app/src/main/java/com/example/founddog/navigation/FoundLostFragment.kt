package com.example.founddog.navigation

import android.content.Intent
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
import com.example.founddog.UploadActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_found_lost.*
import java.lang.Exception

class FoundLostFragment : Fragment() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var radioLost: RadioButton
    private lateinit var radioFound: RadioButton

    private lateinit var fab: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioGroup = view.findViewById(R.id.radiogroup_found_lost)
        radioLost = view.findViewById(R.id.radio_lost)
        radioFound = view.findViewById(R.id.radio_found)

        fab = view.findViewById(R.id.fab)

        childFragmentManager.beginTransaction().add(frame_found_lost.id, LostFragment()).commit()

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            replaceFragment(
                when (checkedId) {
                    R.id.radio_lost -> LostFragment()
                    R.id.radio_found -> FoundFragment()
                    else -> LostFragment()
                }
            )
        }

        fab.setOnClickListener { view ->
            startActivity(Intent(context, UploadActivity::class.java))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_found_lost, container, false)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_found_lost, container, false)
    }


    private fun replaceFragment(fragment: Fragment) {
        Log.d(MainActivity.TAG, "fragment " + fragment)
        childFragmentManager.beginTransaction().replace(frame_found_lost.id, fragment).commit()
    }
}