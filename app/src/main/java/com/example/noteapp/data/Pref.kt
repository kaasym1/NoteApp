package com.example.noteapp.data

import android.content.Context

class Pref(context: Context) {
    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)


    fun isShow(): Boolean {
        return pref.getBoolean(SHOWED_KEY, false)
    }

    fun onShowed() {
        pref.edit().putBoolean(SHOWED_KEY, true).apply()
    }

    companion object {
        const val PREF_NAME = "pref.name"
        const val SHOWED_KEY = "showed.key"
    }
}
