package com.example.data_visuals

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.FileOutputStream

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Save the username and password to a file
                saveCredentials(username, password)

                // Navigate to MainActivity
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Username and password are required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveCredentials(username: String, password: String) {
        val filename = "data.txt"
        val fileContents = "$username\n$password"
        val outputStream: FileOutputStream

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE)
            outputStream.write(fileContents.toByteArray())
            outputStream.close()
            Log.d("LoginActivity", "Username and password saved successfully: $username, $password")
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error saving username and password: ${e.message}")
            e.printStackTrace()
        }
    }

}
