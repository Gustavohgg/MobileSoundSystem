package com.example.mobilesoundsystem.model

data class Produto(
    val codigo: String,
    val nome: String,
    val categoria: String,
    val quantidade: Int,
    val preco: Double,
    val quantidadeMinima: Int,  // Adicionando o campo quantidadeMinima
    val quantidadeMaxima: Int   // Adicionando o campo quantidadeMaxima
)

