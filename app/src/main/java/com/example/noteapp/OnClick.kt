package com.example.noteapp

import androidx.drawerlayout.widget.DrawerLayout
import com.example.noteapp.models.NoteModel
import com.google.android.material.navigation.NavigationView

interface OnClick {
    fun onLongClick(noteModel: NoteModel)

    fun onClick(noteModel: NoteModel)
    fun setupDrawer()
}