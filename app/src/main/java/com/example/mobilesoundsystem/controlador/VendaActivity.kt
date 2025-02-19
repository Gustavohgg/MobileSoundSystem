package com.example.mobilesoundsystem.controlador

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mobilesoundsystem.R
import com.example.mobilesoundsystem.database.DatabaseHelper
import com.example.mobilesoundsystem.model.ItemVenda
import com.example.mobilesoundsystem.model.Produto
import com.example.mobilesoundsystem.model.Venda

class VendaActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venda)

        dbHelper = DatabaseHelper(this)

        // Criar o canal de notificação
        criarCanalNotificacao()

        // Exemplo de venda
        val produto1 = Produto("P001", "Produto A", "Categoria 1", 100, 50.0, 10, 50)
        val produto2 = Produto("P002", "Produto B", "Categoria 2", 50, 30.0, 5, 30)

        // Registrar os produtos no banco de dados
        dbHelper.registrarProduto(produto1)
        dbHelper.registrarProduto(produto2)

        // Itens da venda
        val item1 = ItemVenda(produto1, 2, 0.0) // 2 unidades, sem desconto
        val item2 = ItemVenda(produto2, 1, 5.0) // 1 unidade, 5 de desconto

        val itensVenda = listOf(item1, item2)

        // Calcular o total da venda
        val totalVenda = calcularTotalVenda(itensVenda)

        // Criar um objeto Venda com data, cliente, CPF e total
        val venda = Venda(id = 0, data = "2025-02-10", cliente = "Cliente Exemplo", cpf = "12345678900", total = totalVenda)

        val idVenda = dbHelper.registrarVenda(venda).toLong()

        // Registrar os itens da venda e atualizar o estoque
        for (item in itensVenda) {
            val totalItem = item.calcularPrecoTotal()
            val result = dbHelper.registrarItensVenda(idVenda, item.produto.codigo, item.quantidadeVendida, item.desconto, totalItem)

            // Atualizar o estoque após a venda
            if (result != -1L) {
                val estoqueAtualizado = dbHelper.atualizarProdutoEstoque(item.produto.codigo, item.quantidadeVendida)
                if (estoqueAtualizado) {
                    Toast.makeText(this, "Venda realizada com sucesso!", Toast.LENGTH_LONG).show()
                    verificarEstoqueCritico() // Verificar se o estoque está crítico após a venda
                } else {
                    Toast.makeText(this, "Erro ao atualizar o estoque.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Erro ao registrar item da venda.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Função para calcular o total da venda
    private fun calcularTotalVenda(itensVenda: List<ItemVenda>): Double {
        var total = 0.0
        for (item in itensVenda) {
            total += item.calcularPrecoTotal()
        }
        return total
    }

    // Criar o canal de notificação
    private fun criarCanalNotificacao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nomeCanal = "Estoque Crítico"
            val descricaoCanal = "Notificações de estoque crítico"
            val importancia = NotificationManager.IMPORTANCE_HIGH
            val canal = NotificationChannel("estoque_critico", nomeCanal, importancia).apply {
                description = descricaoCanal
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(canal)
        }
    }

    // Função para verificar estoque crítico
    private fun verificarEstoqueCritico() {
        // Lista de produtos
        val produtos = dbHelper.buscarProdutos()

        // Filtra os produtos com estoque abaixo do mínimo
        val produtosCriticos = produtos.filter { it.quantidade < it.quantidadeMinima }

        if (produtosCriticos.isNotEmpty()) {
            // Se houver produtos críticos, enviar notificação
            enviarNotificacao(produtosCriticos)
        }
    }

    // Função para enviar notificação
    private fun enviarNotificacao(produtosCriticos: List<Produto>) {
        val notificationManager = NotificationManagerCompat.from(this)

        // Criação da notificação
        val notificacao = NotificationCompat.Builder(this, "estoque_critico")
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Usando o ícone padrão
            .setContentTitle("Estoque Crítico")
            .setContentText("Os seguintes produtos estão com estoque baixo: ${produtosCriticos.joinToString { it.nome }}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1001, notificacao)
    }
}