package com.example.mobilesoundsystem.controlador

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilesoundsystem.R

class FuncionariosActivity : AppCompatActivity() {

    private lateinit var btnCadastroGerente: Button
    private lateinit var btnCadastroVendedor: Button
    private lateinit var btnVoltar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funcionarios)

        // Inicializa os botões após setContentView
        btnCadastroGerente = findViewById(R.id.btnCadastroGerente)
        btnCadastroVendedor = findViewById(R.id.btnCadastroVendedor)
        btnVoltar = findViewById(R.id.btnVoltar)

        // Configuração dos cliques nos botões
        btnCadastroGerente.setOnClickListener {
            val telaGerente = Intent(this, CadGerenteActivity::class.java)
            startActivity(telaGerente)
        }

        btnCadastroVendedor.setOnClickListener {
            val telaVendedor = Intent(this, CadVendedorActivity::class.java)
            startActivity(telaVendedor)
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
