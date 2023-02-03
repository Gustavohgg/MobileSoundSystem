package com.example.mobilesoundsystem.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.mobilesoundsystem.R;

public class MainActivity extends AppCompatActivity {

    private Button btnFuncionarios;
    private Button btnEstoque;
    private Button btnCliente;
    private Button btnVendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}