package com.example.noteapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteModel")
data class NoteModel(
    val title: String,
    val description: String,
    val color: Int,
    val time: String,
    val date: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}