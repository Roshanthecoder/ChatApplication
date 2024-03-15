package com.example.notificationscheduler.ui.chat

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notificationscheduler.R
import com.example.notificationscheduler.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Chat : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var fuser: FirebaseUser
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val id = intent.getStringExtra("id") ?: ""
        fuser = FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("Users").child(id)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").getValue(String::class.java)
                val email = snapshot.child("email").getValue(String::class.java)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("roshan", "onCancelled: $error")
            }

        })
        binding.sendBtn.setOnClickListener {
            sendMessage(fuser.uid, id, binding.editTextText4.text.toString())
        }

    }


    private fun sendMessage(sender: String, receiver: String, message: String) {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        val hashmap: HashMap<String, String> = HashMap()
        hashmap["sender"] = sender
        hashmap["receiver"] = receiver
        hashmap["message"] = message
        reference.child("Chats").push().setValue(hashmap)
    }

}