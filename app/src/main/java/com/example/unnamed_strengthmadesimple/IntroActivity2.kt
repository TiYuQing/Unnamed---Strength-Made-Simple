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

class IntroActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro2)

        val name = intent.getStringExtra("name") // Retrieve name

        val weightEditText: EditText = findViewById(R.id.editTextWeight)
        val submitButton: Button = findViewById(R.id.btnSubmit)

        submitButton.setOnClickListener {
            val weight = weightEditText.text.toString().toIntOrNull()

            if (name != null && weight != null) {
                // Create and insert the user data into the database
                val user = User(
                    id = 1,
                    name = name,
                    weight = weight,
                    level = 0,
                    experience = 0,
                    strength = 0,
                )

                GlobalScope.launch {
                    val db = DatabaseProvider.getDatabase(applicationContext)
                    val userDao = db.userDao()

                    // Insert or update user data
                    userDao.insertOrUpdate(user)

                    // Navigate to MainActivity
                    val intent = Intent(this@IntroActivity2, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, "Please enter valid weight", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
