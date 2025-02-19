package com.example.mobilesoundsystem.controlador

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilesoundsystem.R
import com.example.mobilesoundsystem.database.DatabaseHelper
import com.example.mobilesoundsystem.model.Gerente

class CadGerenteActivity : AppCompatActivity() {

    private lateinit var edtIdGerente: EditText
    private lateinit var edtNome: EditText
    private lateinit var edtDataNasc: EditText
    private lateinit var edtTelefone: EditText
    private lateinit var edtEndereco: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtSalario: EditText
    private lateinit var btnGravar: Button
    private lateinit var btnNovo: Button
    private lateinit var btnVoltar: Button
    private lateinit var btnAlterar: Button
    private lateinit var btnExcluir: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastrogerente)

        // Inicializando os botões e campos de entrada
        edtIdGerente = findViewById(R.id.idgerente)
        edtNome = findViewById(R.id.nome)
        edtDataNasc = findViewById(R.id.datanasc)
        edtTelefone = findViewById(R.id.telefone)
        edtEndereco = findViewById(R.id.endereco)
        edtEmail = findViewById(R.id.email)
        edtSalario = findViewById(R.id.salario)
        btnGravar = findViewById(R.id.btnGravar)
        btnNovo = findViewById(R.id.btnCadNovo)
        btnVoltar = findViewById(R.id.btnVoltar)
        btnAlterar = findViewById(R.id.btnAlterar)
        btnExcluir = findViewById(R.id.btnExcluir)

        // Inicializando o helper do banco de dados
        dbHelper = DatabaseHelper(this)

        // Definindo ações dos botões
        btnNovo.setOnClickListener {
            limparCampos()
        }

        btnGravar.setOnClickListener {
            gravarGerente()
        }

        btnVoltar.setOnClickListener {
            finish()  // Fecha a Activity e retorna à tela anterior
        }

        btnAlterar.setOnClickListener {
            alterarGerente()
        }

        btnExcluir.setOnClickListener {
            excluirGerente()
        }
    }

    private fun limparCampos() {
        // Lógica para limpar os campos de entrada
        edtIdGerente.text.clear()
        edtNome.text.clear()
        edtDataNasc.text.clear()
        edtTelefone.text.clear()
        edtEndereco.text.clear()
        edtEmail.text.clear()
        edtSalario.text.clear()
    }

    private fun gravarGerente() {
        val idGerente = edtIdGerente.text.toString()
        val nome = edtNome.text.toString()
        val dataNasc = edtDataNasc.text.toString()
        val telefone = edtTelefone.text.toString()
        val endereco = edtEndereco.text.toString()
        val email = edtEmail.text.toString()
        val salarioString = edtSalario.text.toString()

        if (idGerente.isNotEmpty() && nome.isNotEmpty() && dataNasc.isNotEmpty() &&
            telefone.isNotEmpty() && endereco.isNotEmpty() && email.isNotEmpty() && salarioString.isNotEmpty()) {

            // Converte o salário de String para Double, com valor padrão 0.0 em caso de erro
            val salario = salarioString.toDoubleOrNull() ?: 0.0

            // Cria um objeto Gerente
            val gerente = Gerente(idGerente, nome, dataNasc, telefone, endereco, email, salario)

            // Salva o gerente no banco de dados
            val gerenteGravado = dbHelper.salvarGerente(gerente)
            if (gerenteGravado) {
                Toast.makeText(this, "Gerente cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                limparCampos() // Limpa os campos após salvar
            } else {
                Toast.makeText(this, "Erro ao cadastrar gerente.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun alterarGerente() {
        val idGerente = edtIdGerente.text.toString()
        val nome = edtNome.text.toString()
        val dataNasc = edtDataNasc.text.toString()
        val telefone = edtTelefone.text.toString()
        val endereco = edtEndereco.text.toString()
        val email = edtEmail.text.toString()
        val salarioString = edtSalario.text.toString()

        if (idGerente.isNotEmpty() && nome.isNotEmpty() && dataNasc.isNotEmpty() &&
            telefone.isNotEmpty() && endereco.isNotEmpty() && email.isNotEmpty() && salarioString.isNotEmpty()) {

            // Converte o salário de String para Double, com valor padrão 0.0 em caso de erro
            val salario = salarioString.toDoubleOrNull() ?: 0.0

            // Cria um objeto Gerente
            val gerente = Gerente(idGerente, nome, dataNasc, telefone, endereco, email, salario)

            // Altera o gerente no banco de dados
            val gerenteAlterado = dbHelper.alterarGerente(gerente)
            if (gerenteAlterado) {
                Toast.makeText(this, "Gerente alterado com sucesso!", Toast.LENGTH_SHORT).show()
                limparCampos() // Limpa os campos após alterar
            } else {
                Toast.makeText(this, "Erro ao alterar gerente.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun excluirGerente() {
        val idGerente = edtIdGerente.text.toString()
        if (idGerente.isNotEmpty()) {
            val gerenteExcluido = dbHelper.excluirGerente(idGerente)
            if (gerenteExcluido) {
                Toast.makeText(this, "Gerente excluído com sucesso!", Toast.LENGTH_SHORT).show()
                limparCampos() // Limpa os campos após excluir
            } else {
                Toast.makeText(this, "Erro ao excluir gerente.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Por favor, insira o ID do gerente para excluir.", Toast.LENGTH_SHORT).show()
        }
    }
}