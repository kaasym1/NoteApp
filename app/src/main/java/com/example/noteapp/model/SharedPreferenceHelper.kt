package com.example.noteapp.model

import android.content.Context
import android.content.SharedPreferences
import com.example.noteapp.ui.fragments.SingUpFragment

class SharedPreferenceHelper(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "shared",
        Context.MODE_PRIVATE
    )

    fun setOnBoardingComplete(isComplete: Boolean) {
        sharedPreferences.edit().putBoolean(SHOWED, isComplete).apply()
    }

    fun isOnBoardingComplete(): Boolean {
        return sharedPreferences.getBoolean(SHOWED, false)
    }
    fun setIsGridLayout(isGridLayout: Boolean) {
        sharedPreferences.edit().putBoolean(IS_GRID_LAYOUT, isGridLayout).apply()
    }

    fun getIsGridLayout(): Boolean {
        return sharedPreferences.getBoolean(IS_GRID_LAYOUT, false)
    }

    fun setSignInComplete(isComplete: Boolean) {
        sharedPreferences.edit().putBoolean(SIGN_IN_COMPLETE, isComplete).apply()
    }

    fun isSignInComplete(): Boolean {
        return sharedPreferences.getBoolean(SIGN_IN_COMPLETE, false)
    }

    companion object {
        val SHOWED = "SHOWED"
        val IS_GRID_LAYOUT = "IS_GRID_LAYOUT"
        const val SIGN_IN_COMPLETE = "SIGN_IN_COMPLETE"
    }
}