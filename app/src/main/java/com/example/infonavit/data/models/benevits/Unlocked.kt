package com.example.infonavit.data.models.benevits

data class Unlocked(
    val active: Boolean,
    val ally: Ally,
    val description: String,
    val expiration_date: String,
    val has_coupons: Boolean,
    val id: Int,
    val instructions: String,
    val name: String,
    val primary_color: String,
    val slug: String,
    val territories: List<Territory>,
    val title: String,
    val vector_file_name: String,
    val vector_full_path: String,
    val wallet: Wallet
)