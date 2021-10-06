package com.example.extension

fun String.checkEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()