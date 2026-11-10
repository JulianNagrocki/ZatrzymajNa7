package com.example.zatrzymajna7;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView Licznik, Komunikat, Ltrafien;
    private Button button1, button2;

    private int cyfra = 0;
    private int trafienia = 0;
    private long czasWys = 500;

    private Handler handler;
    private Runnable runnable;
    private boolean czyDziala = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Licznik = findViewById(R.id.Licznik);
        Komunikat = findViewById(R.id.Komunikat);
        Ltrafien = findViewById(R.id.Ltrafien);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        button1.setEnabled(true);
        button2.setEnabled(false);
        Ltrafien.setText("Trafienia: 0");

        handler = new Handler(Looper.getMainLooper());

        runnable = new Runnable() {
            @Override
            public void run() {
                if (cyfra >= 9) {
                    cyfra = 0;
                } else {
                    cyfra++;
                }
                Licznik.setText(String.valueOf(cyfra));

                if (czyDziala) {
                    handler.postDelayed(this, czasWys);
                }
            }
        };

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cyfra = 0;
                Licznik.setText(String.valueOf(cyfra));
                Komunikat.setText("");

                czyDziala = true;
                button1.setEnabled(false);
                button2.setEnabled(true);

                handler.postDelayed(runnable, czasWys);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zatrzymajGre();
            }
        });
    }

    private void zatrzymajGre() {
        if (!czyDziala) return;

        czyDziala = false;
        handler.removeCallbacks(runnable);

        button1.setEnabled(true);
        button2.setEnabled(false);

        if (cyfra == 7) {
            Komunikat.setText("Trafienie!");
            trafienia++;
            Ltrafien.setText("Trafienia: " + trafienia);

            if (czasWys > 200) {
                czasWys -= 50;
            }
        } else {
            Komunikat.setText("Pudło! Zatrzymano na " + cyfra);
        }
    }
}