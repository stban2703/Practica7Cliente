package com.example.practica7;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public class SignupActivity extends AppCompatActivity implements ComunicacionTCP.OnMessageListener {

    private EditText correoText;
    private EditText claveText;
    private EditText confirmarText;
    private Button registrarseBtn;
    private String correo;
    private String clave;
    private String confirmar;
    private String nuevoUsuario;
    private boolean repetido;
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
        repetido = true;
        comm.setObserver(this);
        comm.solicitarConexion();

        registrarseBtn.setOnClickListener(
                (v) -> {
                    correo = correoText.getText().toString();
                    clave = claveText.getText().toString();
                    confirmar = confirmarText.getText().toString();

                    nuevoUsuario = correo + "," + clave;

                    String repetido = comm.getLine();

                    if (clave.equals(confirmar) && (correo.contains("@gmail.com") || correo.contains("@hotmail.com"))
                            || correo.contains("@outlook.com")) {
                        comm.mandarMensaje(nuevoUsuario);


                        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Registro completo");
                        builder.setMessage("Se ha registrado con éxito");
                        builder.show();*/

                        //Pasar actividad
                        /*builder.setPositiveButton("Ok", (dialog, which) -> {
                            dialog.dismiss();
                            Intent i = new Intent(SignupActivity.this, ProfileActivity.class);
                            startActivity(i);
                        });*/

                    } else if (!clave.equals(confirmar)) {
                        runOnUiThread(
                                () -> {
                                    Toast.makeText(this, "Las claves no coinciden", Toast.LENGTH_SHORT).show();
                                }
                        );
                    } else if (!correo.contains("@gmail.com") || !correo.contains("@hotmail.com")
                            || !correo.contains("@outlook.com")) {
                        runOnUiThread(
                                () -> {
                                    Toast.makeText(this, "El correo no es válido", Toast.LENGTH_SHORT).show();
                                }
                        );
                    } else if (correo.equals("") || clave.contains("") || confirmar.contains("")) {
                        runOnUiThread(
                                () -> {
                                    Toast.makeText(this, "Hay campos vacíos", Toast.LENGTH_SHORT).show();
                                }
                        );

                    }

                }

        );
    }

    public boolean isRepetido() {
        return repetido;
    }

    public void setRepetido(boolean repetido) {
        this.repetido = repetido;
    }


    @Override
    public void onMessage(String mensaje) {

        if (mensaje.equals("REPETIDO")) {
            //CUMPLE CON LAS REGLAS DE ANDROID!
            this.runOnUiThread(
                    () -> {
                        Toast.makeText(this, "El correo esta repetiodo", Toast.LENGTH_SHORT).show();
                    }
            );
        }
    }
}
