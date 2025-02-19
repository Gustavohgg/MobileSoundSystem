package com.example.mobilesoundsystem.model


data class Venda(
    val id: Int,
    val data: String,
    val cliente: String,
    val cpf: String,
    val total: Double
)
