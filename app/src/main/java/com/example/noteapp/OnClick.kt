package com.example.noteapp

import com.example.noteapp.models.NoteModel

interface OnClick {
    fun onLongClick(noteModel: NoteModel)

    fun onClick(noteModel: NoteModel)
}