package com.example.mobilesoundsystem.controlador

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilesoundsystem.R
import com.example.mobilesoundsystem.database.DatabaseHelper

class LoginActivity : AppCompatActivity() {

    private lateinit var spinnerTipoUsuario: Spinner
    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtErro: TextView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializa os componentes da tela
        spinnerTipoUsuario = findViewById(R.id.spinnerTipoUsuario)
        edtEmail = findViewById(R.id.edtEmail)
        edtSenha = findViewById(R.id.edtSenha)
        btnLogin = findViewById(R.id.btnLogin)
        txtErro = findViewById(R.id.txtErro)

        // Inicializa o helper do banco de dados
        dbHelper = DatabaseHelper(this)

        // Configura o Spinner com opções de tipo de usuário
        val tiposUsuarios = arrayOf("Diretor", "Gerente", "Vendedor")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tiposUsuarios)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipoUsuario.adapter = adapter

        // Lógica para o botão de login
        btnLogin.setOnClickListener {
            val tipoUsuario = when (spinnerTipoUsuario.selectedItem.toString()) {
                "Diretor" -> DatabaseHelper.DIRETOR
                "Gerente" -> DatabaseHelper.GERENTE
                "Vendedor" -> DatabaseHelper.VENDEDOR
                else -> ""
            }

            val email = edtEmail.text.toString()
            val senha = edtSenha.text.toString()

            // Verifica se todos os campos foram preenchidos
            if (tipoUsuario.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {
                verificarLogin(tipoUsuario, email, senha)
            } else {
                // Exibe mensagem de erro caso algum campo esteja vazio
                txtErro.text = "Preencha todos os campos!"
                txtErro.visibility = View.VISIBLE
            }
        }
    }

    // Função para verificar se o login está correto
    private fun verificarLogin(tipoUsuario: String, email: String, senha: String) {
        val loginValido = dbHelper.verificarLogin(email, senha, tipoUsuario)

        if (loginValido) {
            // Login bem-sucedido, passa o tipo de usuário para MainActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("TIPO_USUARIO", tipoUsuario) // Passando tipo de usuário
            startActivity(intent)
            finish()
        } else {
            // Exibe erro se o login ou a senha estiverem incorretos
            txtErro.text = "Usuário, senha ou tipo de usuário inválido."
            txtErro.visibility = View.VISIBLE
        }
    }

}
