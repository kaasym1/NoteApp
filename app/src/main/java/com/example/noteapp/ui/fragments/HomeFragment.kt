package com.example.noteapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.App
import com.example.noteapp.OnClick
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.model.SharedPreferenceHelper
import com.example.noteapp.models.NoteModel
import com.example.noteapp.ui.adapters.NoteAdapter

class HomeFragment : Fragment(), OnClick {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var noteAdapter: NoteAdapter
    private var isGridLayout: Boolean = false
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private var flag = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferenceHelper = SharedPreferenceHelper(requireContext())
        isGridLayout = sharedPreferenceHelper.getIsGridLayout()
        noteAdapter = NoteAdapter(onLongClick = this, onClick = this)
        initialize()
        setUpListeners()
        getData()
    }

    private fun initialize() {
        binding.rvNotes.apply {
            layoutManager = if (isGridLayout) {
                GridLayoutManager(requireContext(), 2)
            } else {
                LinearLayoutManager(requireContext())
            }
            adapter = noteAdapter
        }
    }

    private fun setUpListeners() = with(binding) {
        btnPlus.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }
        gridLayout.setOnClickListener {
            if (flag) {
                gridLayout.setImageResource(R.drawable.ic_shape2)
                binding.rvNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                sharedPreferenceHelper.setIsGridLayout(isGridLayout)
                flag = false
            } else {
                gridLayout.setImageResource(R.drawable.ic_menu)
                binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
                sharedPreferenceHelper.setIsGridLayout(isGridLayout)
                flag = true
            }
        }

    }

    private fun getData() {
        App().getInstance()?.noteDao()?.getAll()?.observe(viewLifecycleOwner) {
            noteAdapter.submitList(it)
        }
    }

    override fun onLongClick(noteModel: NoteModel) {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle("Вы точно хотите удалить эту заметку?")
            setPositiveButton("Да") { _, _ ->
                App().getInstance()?.noteDao()?.deleteNote(noteModel)
            }
            setNegativeButton("Нет") { dialog, _ ->
                dialog.cancel()
            }
            show()
        }
        builder.create()
    }

    override fun onClick(noteModel: NoteModel) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(noteModel.id)
        findNavController().navigate(action)
    }
}