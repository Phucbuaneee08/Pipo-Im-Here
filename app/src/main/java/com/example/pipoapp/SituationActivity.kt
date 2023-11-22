package com.example.pipoapp

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class SituationActivity : AppCompatActivity() {
    lateinit var backButton : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_situation)
        backButton = findViewById<ImageButton>(R.id.back)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }
}