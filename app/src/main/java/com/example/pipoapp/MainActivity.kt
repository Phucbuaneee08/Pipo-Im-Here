package com.example.pipoapp

import android.Manifest
import android.R.attr
import android.app.DownloadManager.Request
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.pipoapp.databinding.ActivityMainBinding
import java.util.Objects


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        replaceFragments(HomeFragment())
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragments(HomeFragment())
                R.id.information -> replaceFragments(InformationFragment())
                R.id.notification -> replaceFragments(NotificationFragment())
                R.id.settings -> replaceFragments(SettingFragment())
            }
            true
        }
        binding.mic.setOnClickListener {
            val array = arrayOf(Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (checkPermission()) {

            } else {
                ActivityCompat.requestPermissions(this,array, 1)
            }
            startActivity(Intent(this,ChatAIActivity::class.java))
        }
    }

    private fun checkPermission(): Boolean {
        val first =
            ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.RECORD_AUDIO)
        val second = ActivityCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return first == PackageManager.PERMISSION_GRANTED && second == PackageManager.PERMISSION_GRANTED
    }

    private fun replaceFragments(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.framelayout, fragment)
        fragmentTransition.commit()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQ) {
//            if (resultCode === RESULT_OK && attr.data != null) {
//                val result: ArrayList<String> = attr.data.getStringArrayListExtra(
//                    RecognizerIntent.EXTRA_RESULTS
//                )
//                tv_Speech_to_text.setText(
//                    Objects.requireNonNull<ArrayList<String>>(result)[0]
//                )
//            }
//        }
//    }
}