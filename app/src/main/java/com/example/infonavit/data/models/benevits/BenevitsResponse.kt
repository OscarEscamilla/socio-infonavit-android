package com.example.infonavit.data.models.benevits

data class BenevitsResponse(
    val locked: List<Locked>,
    val unlocked: List<Unlocked>
)