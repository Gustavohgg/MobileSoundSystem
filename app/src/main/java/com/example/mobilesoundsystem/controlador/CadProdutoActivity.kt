package com.example.mobilesoundsystem.controlador

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilesoundsystem.R
import com.example.mobilesoundsystem.database.DatabaseHelper
import com.example.mobilesoundsystem.model.Produto

class CadProdutoActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var btnSalvar: Button
    private lateinit var editCodigo: EditText
    private lateinit var editNome: EditText
    private lateinit var editCategoria: EditText
    private lateinit var editQuantidade: EditText
    private lateinit var editPreco: EditText
    private lateinit var editQuantidadeMinima: EditText
    private lateinit var editQuantidadeMaxima: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto)

        // Vinculando as views
        editCodigo = findViewById(R.id.editCodigo)
        editNome = findViewById(R.id.editNome)
        editCategoria = findViewById(R.id.editCategoria)
        editQuantidade = findViewById(R.id.editQuantidade)
        editPreco = findViewById(R.id.editPreco)
        editQuantidadeMinima = findViewById(R.id.editQuantidadeMinima)
        editQuantidadeMaxima = findViewById(R.id.editQuantidadeMaxima)
        btnSalvar = findViewById(R.id.btnSalvar)

        dbHelper = DatabaseHelper(this)

        btnSalvar.setOnClickListener {
            val codigo = editCodigo.text.toString().trim()
            val nome = editNome.text.toString().trim()
            val categoria = editCategoria.text.toString().trim()
            val quantidade = editQuantidade.text.toString().toIntOrNull()
            val preco = editPreco.text.toString().toDoubleOrNull()
            val quantidadeMinima = editQuantidadeMinima.text.toString().toIntOrNull()
            val quantidadeMaxima = editQuantidadeMaxima.text.toString().toIntOrNull()

            if (codigo.isNotEmpty() && nome.isNotEmpty() && categoria.isNotEmpty() &&
                quantidade != null && preco != null &&
                quantidadeMinima != null && quantidadeMaxima != null) {

                // Criando o objeto Produto corretamente
                val produto = Produto(
                    codigo = codigo,
                    nome = nome,
                    categoria = categoria,
                    quantidade = quantidade,
                    preco = preco,
                    quantidadeMinima = quantidadeMinima,
                    quantidadeMaxima = quantidadeMaxima
                )

                // Salvando o produto no banco de dados
                val result = dbHelper.registrarProduto(produto)

                if (result != -1L) {
                    Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao cadastrar o produto.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
