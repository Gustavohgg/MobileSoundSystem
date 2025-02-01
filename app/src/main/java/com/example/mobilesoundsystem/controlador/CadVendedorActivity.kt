package com.example.mobilesoundsystem.controlador

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilesoundsystem.R

class CadVendedorActivity : AppCompatActivity() {

    // Declare os botões
    private lateinit var btnCadNovo: Button
    private lateinit var btnGravar: Button
    private lateinit var btnAlterar: Button
    private lateinit var btnExcluir: Button
    private lateinit var btnVoltar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastrovendedor) // Certifique-se de que o XML está correto

        // Inicialize os botões
        btnCadNovo = findViewById(R.id.btnCadNovo)
        btnGravar = findViewById(R.id.btnGravar)
        btnAlterar = findViewById(R.id.btnAlterar)
        btnExcluir = findViewById(R.id.btnExcluir)
        btnVoltar = findViewById(R.id.btnVoltar)

        // Ações para os botões
        btnCadNovo.setOnClickListener {
            // Aqui você pode definir a ação para criar um novo vendedor
            Toast.makeText(this, "Novo Vendedor", Toast.LENGTH_SHORT).show()
        }

        btnGravar.setOnClickListener {
            // Ação para gravar os dados
            Toast.makeText(this, "Vendedor Gravado", Toast.LENGTH_SHORT).show()
        }

        btnAlterar.setOnClickListener {
            // Ação para alterar os dados do vendedor
            Toast.makeText(this, "Vendedor Alterado", Toast.LENGTH_SHORT).show()
        }

        btnExcluir.setOnClickListener {
            // Ação para excluir os dados do vendedor
            Toast.makeText(this, "Vendedor Excluído", Toast.LENGTH_SHORT).show()
        }

        btnVoltar.setOnClickListener {
            // Ação para voltar para a tela anterior
            finish()  // Fecha a Activity e retorna à tela anterior
        }
    }
}
