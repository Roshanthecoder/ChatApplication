package com.example.notificationscheduler

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationscheduler.databinding.ItemLayoutBinding
import com.example.notificationscheduler.ui.chat.Chat
import com.google.firebase.database.ValueEventListener

class UserAdapter(private val context:Context,private val userList:  ArrayList<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.bind(currentUser)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            // Bind user data to UI elements here
            binding.textViewName.text=user.name
            binding.textViewEmail.text=user.email
            binding.cardclick.setOnClickListener {
                val intent=Intent(context,Chat::class.java)
                intent.putExtra("id",user.id)
                context.startActivity(intent)
            }

            // Similarly bind other user details like email, profile picture URL, etc.
        }
    }
}
