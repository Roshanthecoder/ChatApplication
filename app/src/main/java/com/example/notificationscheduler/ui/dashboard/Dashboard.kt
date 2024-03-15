package com.example.notificationscheduler.ui.dashboard

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.notificationscheduler.R
import com.example.notificationscheduler.User
import com.example.notificationscheduler.UserAdapter
import com.example.notificationscheduler.databinding.ActivityDashboardBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("Users")
    val arrayList = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Now you can add a ValueEventListener to fetch the data
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                arrayList.clear()                // Loop through all the children of the "Users" node
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    user?.let { arrayList.add(it) }
                    // Use this data as needed
                }
                val adapter = UserAdapter(this@Dashboard,arrayList)
                binding.recyclerview.adapter = adapter
                binding.recyclerview.addItemDecoration(DividerItemDecoration(this@Dashboard,DividerItemDecoration.VERTICAL))
                Log.e("roshan", "arraylist $arrayList")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("roshan", "databaseError $databaseError")
                // Handle error
            }
        })
    }
}