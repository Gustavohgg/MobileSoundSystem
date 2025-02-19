package com.example.mobilesoundsystem.model

data class Cliente(
    val id: Int = 0, // Será gerado automaticamente pelo banco
    val nome: String,
    val cpf: String,
    val telefone: String,
    val email: String
)
