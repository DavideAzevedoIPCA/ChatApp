package com.example.chatapp

import android.view.View
import androidx.fragment.app.Fragment

interface IAuthentication {

    fun registerUser(v: View)

    fun loginUser(v: View)
}