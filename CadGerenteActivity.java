package com.example.mobilesoundsystem.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilesoundsystem.R;

public class CadGerenteActivity extends AppCompatActivity {

    Button btnGravar;
    Button btnNovo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrogerente);

        btnGravar = findViewById(R.id.btnGravar);
        btnNovo = findViewById(R.id.btnCadNovo);

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }




}
