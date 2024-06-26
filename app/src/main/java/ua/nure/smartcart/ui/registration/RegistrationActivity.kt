package ua.nure.smartcart.ui.registration

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ua.nure.apiclient.service.RegistrationService
import ua.nure.smartcart.R

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usernameEditText = findViewById<EditText>(R.id.edit_text_username)
        val emailEditText = findViewById<EditText>(R.id.edit_text_email)
        val passwordEditText = findViewById<EditText>(R.id.edit_text_password)
        val registerButton = findViewById<Button>(R.id.button_register)
        val fullNameEditText = findViewById<EditText>(R.id.edit_text_full_name)
        val backButton = findViewById<Button>(R.id.back_to_login_button)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val fullName = fullNameEditText.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && fullName.isNotEmpty()) {
                RegistrationService().register(email, username, password, fullName)
                Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}
