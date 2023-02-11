package com.example.mobilesoundsystem.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilesoundsystem.R;

public class FuncionariosActivity extends AppCompatActivity {

    Button btnCadastroGerente;
    Button btnCadastroVendedor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionarios);




        btnCadastroGerente.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick (View vìew){
            Intent telaGerente = new Intent(getApplicationContext(), CadGerenteActivity.class);
            startActivity(telaGerente);
    }
    });

        btnCadastroVendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telaVendedor = new Intent(getApplicationContext(), CadVendedorActivity.class);
                startActivity(telaVendedor);
            }
        });
}
}
