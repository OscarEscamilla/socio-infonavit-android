package com.example.infonavit.data.models.login

data class LoginResponse(
    val email: String,
    val id: Int,
    val member: Member,
    val role: String,
    val sign_in_count: Int,
    var token: String = ""
)