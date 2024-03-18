package com.example.credentials

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.credentials.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var defaultUsername = "user"
    private var defaultPassword = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null) {
            defaultUsername = bundle.getString("username").toString()
            defaultPassword = bundle.getString("password").toString()
        }

        binding.usernameEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            sendDataToCredentials(username, password)
        }

        updateButtonState()
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            updateButtonState()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun updateButtonState() {
        val username = binding.usernameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        binding.loginButton.isEnabled = username.isNotBlank() && password.isNotBlank()
    }

    private fun sendDataToCredentials(username: String, password: String) {
        if(username == defaultUsername && password == defaultPassword) {
            val bundle = Bundle()
            bundle.putString("username", username)
            bundle.putString("password", password)

            val intent = Intent(this, CredentialsActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        } else {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder
                .setMessage("The username and/or password are incorrect!")
                .setTitle("Error: invalid credentials")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.cancel()
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

    }
}
