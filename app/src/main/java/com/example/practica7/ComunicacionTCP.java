package com.example.practica7;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ComunicacionTCP extends Thread {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private SignupActivity app;
    private OnMessageListener observer;

    private String line;

    public void setObserver(OnMessageListener observer) {
        this.observer = observer;
    }

    public ComunicacionTCP(SignupActivity app) {
        this.app = app;
    }

    //Hilo de recepción
    @Override
    public void run() {
        try {
            this.socket = new Socket("10.0.2.2", 5000);

            //Reader
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            this.reader = new BufferedReader(isr);

            //Writer
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            this.writer = new BufferedWriter(osw);

            while (true) {
                recibirMensaje();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Solicitar conexion
    public void solicitarConexion(/*String ip*/) {
        //this.ip = ip;
        this.start();
    }

    //Mandar un mensaje
    public void mandarMensaje(String mensaje) {
        new Thread(
                () -> {
                    try {
                        writer.write(mensaje + "\n");
                        writer.flush();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
        ).start();
    }

    //Recibir mensaje
    public void recibirMensaje() throws IOException {
        line = reader.readLine();

        if (line.equals("REPETIDO")) {
            app.runOnUiThread(
                    () -> {
                        Toast.makeText(app, "El correo esta repetido", Toast.LENGTH_LONG).show();
                    }
            );
        } else if (line.equals("OK")) {
            /*AlertDialog.Builder builder = new AlertDialog.Builder(app);
            builder.setTitle("Registro completo");
            builder.setMessage("Se ha registrado con éxito");
            builder.show();*/

            app.runOnUiThread(
                    () -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(app);
                        builder.setTitle("Registro completo");
                        builder.setMessage("Se ha registrado con éxito");
                        builder.show();
                        //Toast.makeText(app, "OK", Toast.LENGTH_LONG).show();
                    }
            );

        }


        observer.onMessage(line);
    }

    public void cerrarConexion() {
        try {
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getLine() {
        return line;
    }

    public interface OnMessageListener {
        void onMessage(String mensaje);
    }
}
