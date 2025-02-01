package com.example.mobilesoundsystem.controlador

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilesoundsystem.R
import com.example.mobilesoundsystem.database.DatabaseHelper

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
            // Ação para voltar para a tela anterior
            finish()  // Fecha a Activity e retorna à tela anterior
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
        val salario = edtSalario.text.toString()

        // Verifica se todos os campos estão preenchidos
        if (idGerente.isNotEmpty() && nome.isNotEmpty() && dataNasc.isNotEmpty() &&
            telefone.isNotEmpty() && endereco.isNotEmpty() && email.isNotEmpty() && salario.isNotEmpty()) {

            val gerenteGravado = dbHelper.salvarGerente(idGerente, nome, dataNasc, telefone, endereco, email, salario)
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
}
