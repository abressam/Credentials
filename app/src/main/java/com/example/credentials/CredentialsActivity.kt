package com.example.credentials

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.credentials.databinding.ActivityCredentialsBinding

class CredentialsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCredentialsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCredentialsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null) {
            binding.usernameEditText.setText(bundle.getString("username")).toString()
            binding.passwordEditText.setText(bundle.getString("password")).toString()
        }

        binding.usernameEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)

        binding.saveButton.setOnClickListener {
            val newUsername = binding.usernameEditText.text.toString()
            val newPassword = binding.passwordEditText.text.toString()

            sendNewCredentials(newUsername, newPassword)
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
        binding.saveButton.isEnabled = username.isNotBlank() && password.isNotBlank()
    }

    private fun sendNewCredentials(newUsername: String, newPassword: String) {
        val bundle = Bundle()
        bundle.putString("username", newUsername)
        bundle.putString("password", newPassword)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setMessage("You set a new username and password!")
            .setTitle("Success")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.cancel()

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}