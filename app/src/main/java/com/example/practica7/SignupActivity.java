package com.example.practica7;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public class SignupActivity extends AppCompatActivity {

    private EditText correoText;
    private EditText claveText;
    private EditText confirmarText;
    private Button registrarseBtn;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private ComunicacionTCP comm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        correoText = findViewById(R.id.correoText);
        claveText = findViewById(R.id.claveText);
        confirmarText = findViewById(R.id.confirmarText);
        registrarseBtn = findViewById(R.id.registrarseBtn);
        comm = new ComunicacionTCP(this);

        registrarseBtn.setOnClickListener(
                (v) -> {
                    comm.solicitarConexion();
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Registro completo");
                    builder.setMessage("Se ha registrado con éxito");
                    builder.setPositiveButton("Ok", (dialog, which) -> {
                        dialog.dismiss();
                        Intent i = new Intent(SignupActivity.this, ProfileActivity.class);
                        startActivity(i);
                    });
                    builder.show();
                }
        );
    }
}
