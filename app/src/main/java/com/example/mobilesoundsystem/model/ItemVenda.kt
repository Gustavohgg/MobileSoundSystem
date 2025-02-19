package com.example.mobilesoundsystem.model

data class ItemVenda(
    val produto: Produto,
    val quantidadeVendida: Int,
    val desconto: Double
) {
    fun calcularPrecoTotal(): Double {
        val precoComDesconto = produto.preco * (1 - desconto / 100)
        return precoComDesconto * quantidadeVendida
    }
}