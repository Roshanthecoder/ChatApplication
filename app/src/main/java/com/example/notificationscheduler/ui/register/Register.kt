package com.example.notificationscheduler.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notificationscheduler.R
import com.example.notificationscheduler.databinding.ActivityRegisterBinding
import com.example.notificationscheduler.ui.login.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private var reference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()

        binding.loginBTn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
        binding.button.setOnClickListener {
            binding.progresbar.visibility = View.VISIBLE
            register(
                binding.editTextText.text.toString(),
                binding.editTextTextEmailAddress.text.toString(),
                binding.editTextNumberPassword.text.toString(),
                binding.editTextPhone.text.toString()
            )
        }
    }

    private fun register(name: String, email: String, password: String, phone: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            binding.progresbar.visibility = View.GONE // Set visibility once

            if (task.isSuccessful) {
                val firebaseUser: FirebaseUser? = auth.currentUser
                val userID = firebaseUser?.uid ?: ""
                reference = FirebaseDatabase.getInstance().getReference("Users").child(userID)

                // Use apply to initialize hashMap
                val hashMap = HashMap<String, String>().apply {
                    put("id", userID)
                    put("name", name)
                    put("phone", phone)
                    put("email", email)
                    put("password", password)
                }

                reference?.setValue(hashMap)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this@Register, Login::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "task unsuccesful due to ${task.exception}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}