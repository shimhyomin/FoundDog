package com.example.founddog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.ui.NavigationUI
import com.example.founddog.navigation.AlarmFragment
import com.example.founddog.navigation.FoundLostFragment
import com.example.founddog.navigation.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarItemView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG: String = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "Main : start")

        supportFragmentManager.beginTransaction().add(frame_content.id, HomeFragment()).commit()
        Log.d(TAG, "Main : start1")
        bottom_navigation.setOnItemSelectedListener {
            Log.d(TAG, "item_id : "+it.itemId)
            replaceFragment(
                when(it.itemId){
                    R.id.menu_home -> HomeFragment()
                    R.id.menu_found_lost -> FoundLostFragment()
                    R.id.menu_alarm -> AlarmFragment()
                    else -> HomeFragment()
                }
            )
            Log.d(TAG, "Main : start2")
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        Log.d(TAG, "fragment "+fragment)
        supportFragmentManager.beginTransaction().replace(frame_content.id, fragment).commit()
    }

}