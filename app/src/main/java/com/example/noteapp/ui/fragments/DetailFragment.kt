package com.example.noteapp.ui.fragments

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.noteapp.databinding.FragmentDetailBinding
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.models.NoteModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private var colorResource: Int = R.color.white2
    private var noteId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        update()
        setUpListeners()
        displayCurrentDateTime()
    }

    private fun update() {
        arguments?.let {
            noteId = it.getInt("noteId", -1)
        }
        if (noteId != -1) {
            val note = App().getInstance()?.noteDao()?.getNoteById(noteId)
            note?.let { model ->
                binding.etTitle.setText(model.title)
                binding.etDescription.setText(model.description)
                colorResource = model.color
                when (colorResource) {
                    R.color.black2 -> binding.rb1.isChecked = true
                    R.color.white2 -> binding.rb2.isChecked = true
                    R.color.red -> binding.rb3.isChecked = true
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpListeners() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnAdd.visibility =
                    if (binding.etTitle.text.isNotEmpty() || binding.etDescription.text.isNotEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.etTitle.addTextChangedListener(textWatcher)
        binding.etDescription.addTextChangedListener(textWatcher)

        binding.btnAdd.setOnClickListener {
            val etTitle = binding.etTitle.text.toString()
            val etDescription = binding.etDescription.text.toString()
            val itemDate = binding.txtDate.text.toString()
            val itemTime = binding.txtTime.text.toString()

            val noteModel = NoteModel(
                title = etTitle,
                description = etDescription,
                date = itemDate,
                time = itemTime,
                color = colorResource
            )

            if (noteId != -1) {
                noteModel.id = noteId
                App().getInstance()?.noteDao()?.updateNote(noteModel)
            } else {
                App().getInstance()?.noteDao()?.insertNote(noteModel)
            }
            findNavController().navigateUp()
        }

        binding.returnBtn.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
        }
        binding.btnAdd.visibility = View.GONE

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            colorResource = when (checkedId) {
                binding.rb1.id -> R.color.black2
                binding.rb2.id -> R.color.white2
                binding.rb3.id -> R.color.red
                else -> R.color.white2
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayCurrentDateTime() {
        val currentDateTime = LocalDateTime.now()
        val formatterDate = DateTimeFormatter.ofPattern("dd MMMM")
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
        val formattedDate = currentDateTime.format(formatterDate)
        val formattedTime = currentDateTime.format(formatterTime)
        binding.txtDate.text = formattedDate
        binding.txtTime.text = formattedTime
    }
}