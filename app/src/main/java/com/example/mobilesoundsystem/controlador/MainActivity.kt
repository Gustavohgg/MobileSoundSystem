package com.example.mobilesoundsystem.controlador

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilesoundsystem.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnFuncionarios: Button
    private lateinit var btnEstoque: Button
    private lateinit var btnCliente: Button
    private lateinit var btnVendas: Button
    private lateinit var btnLogout: Button
    private lateinit var tipoUsuario: String // Variável para armazenar o tipo de usuário

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa os botões
        btnFuncionarios = findViewById(R.id.btnFuncionarios)
        btnEstoque = findViewById(R.id.btnEstoque)
        btnCliente = findViewById(R.id.btnCliente)
        btnVendas = findViewById(R.id.btnVendas)
        btnLogout = findViewById(R.id.btnLogout)

        // Recupera o tipo de usuário passado do LoginActivity
        tipoUsuario = intent.getStringExtra("TIPO_USUARIO") ?: ""

        // Lógica para mostrar ou ocultar o botão de funcionários com base no tipo de usuário
        if (tipoUsuario != "Diretor" && tipoUsuario != "Gerente") {
            btnFuncionarios.visibility = View.GONE // Oculta o botão se não for Diretor ou Gerente
        } else {
            btnFuncionarios.visibility = View.VISIBLE // Exibe o botão se for Diretor ou Gerente
        }

        // Evento para acessar a tela de cadastro de funcionários
        btnFuncionarios.setOnClickListener {
            val telaFuncionarios = Intent(this, FuncionariosActivity::class.java)
            startActivity(telaFuncionarios)
        }

        btnLogout.setOnClickListener{
            val telaLogin = Intent(this, LoginActivity::class.java)
            startActivity(telaLogin)
        }
        // Lógica para outros botões, se necessário
        // Por exemplo, você pode adicionar mais verificações ou ações para os outros botões aqui
    }
}
