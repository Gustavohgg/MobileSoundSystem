package com.example.mobilesoundsystem.controlador

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilesoundsystem.R
import com.example.mobilesoundsystem.adapter.ClienteAdapter
import com.example.mobilesoundsystem.database.DatabaseHelper
import com.example.mobilesoundsystem.model.Cliente

class CadClienteActivity : AppCompatActivity() {

    private lateinit var edtNome: EditText
    private lateinit var edtCPF: EditText
    private lateinit var edtTelefone: EditText
    private lateinit var edtEmail: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnInserirCliente: Button
    private lateinit var btnAtualizarCliente: Button
    private lateinit var btnRemoverCliente: Button
    private lateinit var btnBuscarCliente: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)

        edtNome = findViewById(R.id.edtNome)
        edtCPF = findViewById(R.id.edtCPF)
        edtTelefone = findViewById(R.id.edtTelefone)
        edtEmail = findViewById(R.id.edtEmail)
        btnInserirCliente = findViewById(R.id.btnInserirCliente)
        btnAtualizarCliente = findViewById(R.id.btnAtualizarCliente)
        btnRemoverCliente = findViewById(R.id.btnRemoverCliente)
        btnBuscarCliente = findViewById(R.id.btnBuscarCliente)
        recyclerView = findViewById(R.id.recyclerViewClientes)

        recyclerView.layoutManager = LinearLayoutManager(this)
        dbHelper = DatabaseHelper(this)
        carregarListaClientes()

        btnInserirCliente.setOnClickListener { inserirCliente() }
        btnAtualizarCliente.setOnClickListener { atualizarCliente() }
        btnBuscarCliente.setOnClickListener { buscarCliente() }
    }

    override fun onResume() {
        super.onResume()
        carregarListaClientes()
    }

    private fun carregarListaClientes() {
        val clientes = dbHelper.buscarClientes()
        recyclerView.adapter = ClienteAdapter(clientes,
            onEditClick = { cliente -> editarCliente(cliente) },
            onDeleteClick = { cliente -> excluirCliente(cliente) }
        )
    }

    private fun editarCliente(cliente: Cliente) {
        edtNome.setText(cliente.nome)
        edtCPF.setText(cliente.cpf)
        edtTelefone.setText(cliente.telefone)
        edtEmail.setText(cliente.email)
    }

    private fun excluirCliente(cliente: Cliente) {
        val sucesso = dbHelper.removerCliente(cliente.id)
        if (sucesso) {
            Toast.makeText(this, "Cliente removido com sucesso!", Toast.LENGTH_SHORT).show()
            carregarListaClientes()
        } else {
            Toast.makeText(this, "Erro ao remover cliente!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buscarCliente() {
        val cpf = edtCPF.text.toString()
        if (cpf.isNotEmpty()) {
            val cliente = dbHelper.buscarClientePorCpf(cpf)
            if (cliente != null) {
                editarCliente(cliente)
                Toast.makeText(this, "Cliente encontrado!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Cliente não encontrado!", Toast.LENGTH_SHORT).show()
                limparCampos()
            }
        } else {
            Toast.makeText(this, "Informe o CPF para buscar!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inserirCliente() {
        val nome = edtNome.text.toString()
        val cpf = edtCPF.text.toString()
        val telefone = edtTelefone.text.toString()
        val email = edtEmail.text.toString()
        if (nome.isNotEmpty() && cpf.isNotEmpty() && telefone.isNotEmpty() && email.isNotEmpty()) {
            val cliente = Cliente(nome = nome, cpf = cpf, telefone = telefone, email = email)
            val sucesso = dbHelper.inserirCliente(cliente)
            if (sucesso) {
                Toast.makeText(this, "Cliente inserido com sucesso!", Toast.LENGTH_SHORT).show()
                limparCampos()
                carregarListaClientes()
            } else {
                Toast.makeText(this, "Erro ao inserir cliente!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun atualizarCliente() {
        val nome = edtNome.text.toString()
        val cpf = edtCPF.text.toString()
        val telefone = edtTelefone.text.toString()
        val email = edtEmail.text.toString()
        if (nome.isNotEmpty() && cpf.isNotEmpty() && telefone.isNotEmpty() && email.isNotEmpty()) {
            val clienteExistente = dbHelper.buscarClientePorCpf(cpf)
            if (clienteExistente != null) {
                val clienteAtualizado = Cliente(
                    id = clienteExistente.id,
                    nome = nome,
                    cpf = cpf,
                    telefone = telefone,
                    email = email
                )
                val sucesso = dbHelper.atualizarCliente(clienteAtualizado)
                if (sucesso) {
                    Toast.makeText(this, "Cliente atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                    limparCampos()
                    carregarListaClientes()
                } else {
                    Toast.makeText(this, "Erro ao atualizar cliente!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Cliente não encontrado!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limparCampos() {
        edtNome.text.clear()
        edtCPF.text.clear()
        edtTelefone.text.clear()
        edtEmail.text.clear()
    }
}
