package com.example.pipoapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*


class ChatAIActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    val url = "https://api.openai.com/v1/engines/text-davinci-003/completions"
    // creating variables on below line.
    lateinit var txtResponse: TextView
    lateinit var idTVQuestion: TextView
    lateinit var micIV: ImageView
    lateinit var etQuestion: TextInputEditText
    lateinit var btnBack: ImageButton
    private val REQUEST_CODE_SPEECH_INPUT = 111


    private val speechRecognitionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            onSpeechRecognitionResult(result.resultCode, result.data)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatai)
        etQuestion = findViewById<TextInputEditText>(R.id.etQuestion)
        //val btnSubmit=findViewById<Button>(R.id.btnSubmit)
        idTVQuestion = findViewById<TextView>(R.id.idTVQuestion)
        txtResponse = findViewById<TextView>(R.id.txtResponse)
        btnBack = findViewById<ImageButton>(R.id.btnBack)

        micIV = findViewById(R.id.idIVMic)
        /** btnSubmit.setOnClickListener {
        val question=etQuestion.text.toString().trim()
        Toast.makeText(this,question, Toast.LENGTH_SHORT).show()
        if(question.isNotEmpty()){
        getResponse(question) { response ->
        runOnUiThread {
        txtResponse.text = response
        }
        }
        }
        } */
        btnBack.setOnClickListener {
            onBackPressed()
        }
        etQuestion.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {

                // setting response tv on below line.
                txtResponse.text = "Vui lòng chờ.."

                // validating text
                val question = etQuestion.text.toString().trim()
                Toast.makeText(this, question, Toast.LENGTH_SHORT).show()
                if (question.isNotEmpty()) {
                    getResponse(question) { response ->
                        runOnUiThread {
                            txtResponse.text = response
                        }
                    }
                }
                return@OnEditorActionListener true
            }
            false
        })



        micIV.setOnClickListener {
            // on below line we are calling speech recognizer intent.
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            val re = ""

            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something..")
            try {
                speechRecognitionLauncher.launch(intent)
            } catch (e: Exception) {
                // on below line we are displaying error message in toast
                Toast
                    .makeText(
                        this@ChatAIActivity, " " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // in this method we are checking request
        // code with our result code.
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            // on below line we are checking if result code is ok
            if (resultCode == RESULT_OK && data != null) {

                // in that case we are extracting the
                // data from our array list
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                // on below line we are setting data
                // to our output text view.
                idTVQuestion.setText(
                    Objects.requireNonNull(res)[0]
                )
            }
        }
    }

    private fun onSpeechRecognitionResult(resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && data != null) {
            val res: ArrayList<String>? =
                data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            idTVQuestion.text = res?.get(0) ?: "No speech recognition result"
        }
    }

    fun getResponse(question: String, callback: (String) -> Unit) {
        idTVQuestion.text = question
        etQuestion.setText("")
        val apiKey = "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"
        val requestBody =
            """
            {
                "prompt": "$question",
                "max_tokens": 1000,
                "temperature": 0
            }
            """.trimIndent()

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", "API failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if (body != null) {
                    Log.v("data", body)
                } else {
                    Log.v("data", "empty")
                }
                val jsonObject = JSONObject(body)
                val jsonArray: JSONArray = jsonObject.getJSONArray("choices")
                val textResult = jsonArray.getJSONObject(0).getString("text")
                callback(textResult)
            }
        })
    }


}