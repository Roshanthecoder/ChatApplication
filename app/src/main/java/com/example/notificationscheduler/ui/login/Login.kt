package com.example.notificationscheduler.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notificationscheduler.R
import com.example.notificationscheduler.databinding.ActivityLoginBinding
import com.example.notificationscheduler.ui.dashboard.Dashboard
import com.example.notificationscheduler.ui.register.Register
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        binding.regsiterBTn.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
            finish()
        }
        binding.button2.setOnClickListener {
            binding.progresbar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(
                binding.editTextText2.text.toString(),
                binding.editTextTextPassword.text.toString()
            ).addOnCompleteListener { task ->
                binding.progresbar.visibility = View.GONE
                if (task.isSuccessful) {
                    // You can get other user data such as display name, photo URL, etc. here
                    // For example:
                    val intent = Intent(this@Login, Dashboard::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Authentication Issue ${task.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}