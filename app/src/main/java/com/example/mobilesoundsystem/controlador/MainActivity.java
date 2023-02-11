package com.example.mobilesoundsystem.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobilesoundsystem.R;

public abstract class MainActivity extends AppCompatActivity {

    private Button btnFuncionarios;
    private Button btnEstoque;
    private Button btnCliente;
    private Button btnVendas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFuncionarios = findViewById(R.id.btnFuncionarios);
        btnEstoque = findViewById(R.id.btnEstoque);
        btnCliente = findViewById(R.id.btnCliente);
        btnVendas = findViewById(R.id.btnVendas);

        //Evento para acessar a tela de cadastro de funcionarios

        btnFuncionarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telaFuncionarios = new Intent(getApplicationContext(), FuncionariosActivity.class);
                startActivity(telaFuncionarios);
            }
        });


    }




}