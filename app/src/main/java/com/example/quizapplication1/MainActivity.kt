package com.example.quizapplication1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.ComponentActivity
import android.widget.Button
import android.widget.Toast




class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

                val edName: EditText = findViewById(R.id.ed_name)
                val btnStart: Button = findViewById(R.id.btn_start)
                btnStart.setOnClickListener {
                    if(edName.text.isNotEmpty()) {
                        val intent = Intent(this, QuizActivity::class.java)
                        intent.putExtra(Constants.USER_NAME, edName.text.toString())
                        startActivity(intent)
                    } else {
                        Toast.makeText(this,"Bitte geben hier Ihren Namen ein", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }






