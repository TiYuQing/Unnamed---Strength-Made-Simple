package com.example.unnamed_strengthmadesimple

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unnamed_strengthmadesimple.database.DatabaseProvider
import com.example.unnamed_strengthmadesimple.database.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IntroActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseProvider.getDatabase(this)
        setContentView(R.layout.activity_intro1)

        val nameEditText: EditText = findViewById(R.id.editTextName)
        val submitButton: Button = findViewById(R.id.btnSubmit)

        submitButton.setOnClickListener {
            val name = nameEditText.text.toString()

            if (name.isNotEmpty()) {
                // Pass name to IntroActivity2 using intent extras
                val intent = Intent(this@IntroActivity1, IntroActivity2::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
                finish() // Finish IntroActivity1
            } else {
                Toast.makeText(this, "Please enter valid name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

//        // Add a delay or implement logic to navigate to MainActivity
//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish() // Ensure this activity is removed from the back stack
//        }, 2000) // Adjust the delay duration as needed