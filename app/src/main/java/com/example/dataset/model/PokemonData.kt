package com.example.dataset.model

data class Pokemon(
    val Num: Int,
    val Name: String,
    val Type1: String,
    val Type2: String?,
    val HP: Int,
    val Attack: Int,
    val Defense: Int,
    val SpAtk: Int,
    val SpDef: Int,
    val Speed: Int,
    val Generation: Int,
    val Legendary: String
)

data class ApiResponse<T>(
    val status: String,
    val count: Int?,
    val data: List<T>?,
    val message: String?,
    val error: String?
)