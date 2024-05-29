package com.example.noteapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.OnClick
import com.example.noteapp.R
import com.example.noteapp.databinding.ItemNotesBinding
import com.example.noteapp.models.NoteModel

class NoteAdapter(private val onLongClick: OnClick, private val onClick: OnClick) :
    ListAdapter<NoteModel, NoteAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoteModel) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
            binding.tvData.text = item.date
            binding.tvTime.text = item.time
            binding.itemNotes.setBackgroundColor(item.color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnLongClickListener {
            onLongClick.onLongClick(getItem(position))
            true
        }

        holder.itemView.setOnClickListener {
            onClick.onClick(getItem(position))
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteModel>() {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
