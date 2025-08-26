package com.example.instagramclone

import FeedFragment
import ProfileFragment
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.instagramclone.auth.LoginActivity
import com.example.instagramclone.fragments.SearchFragment
import com.example.instagramclone.fragments.UploadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottomNav)

        // Load FeedFragment first
        openFragment(FeedFragment())

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_feed -> openFragment(FeedFragment())
                R.id.menu_search -> openFragment(SearchFragment())
                R.id.menu_upload -> openFragment(UploadFragment())
                R.id.menu_profile -> openFragment(ProfileFragment())
            }
            true
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
