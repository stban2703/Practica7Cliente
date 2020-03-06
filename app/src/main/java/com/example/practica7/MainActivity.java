package com.example.practica7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button registroBtn;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registroBtn = findViewById(R.id.registroBtn);
        loginBtn = findViewById(R.id.loginBtn);

        registroBtn.setOnClickListener(
                (v) -> {
                    Intent i = new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(i);
                }
        );
    }
}
