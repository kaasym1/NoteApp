package com.example.noteapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.databinding.FragmentChatBinding
import com.example.noteapp.ui.adapter.ChatAdapter
import com.google.firebase.Firebase
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val chatAdapter = ChatAdapter()
    private val db = Firebase.firestore
    private lateinit var query: Query
    private lateinit var listener: ListenerRegistration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setUpListener()
        observeMessage()
    }

    private fun initialize() {
        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatAdapter
        }
    }

    private fun setUpListener() {
        binding.btnSend.setOnClickListener {
            val user = hashMapOf(
                "name" to binding.etChat.text.toString()
            )
            db.collection("user").add(user).addOnCompleteListener {}
            binding.etChat.text.clear()
        }
    }

    private fun observeMessage() {
        query = db.collection("user")
        listener = query.addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            value?.let { snapshot ->
                val messages = mutableListOf<String>()
                for (doc in snapshot.documents) {
                    val name = doc.getString("name")
                    name?.let {
                        messages.add(it)
                    }
                    chatAdapter.submitList(messages)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener.remove()
    }
}