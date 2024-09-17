package com.example.baith1_bai2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvThread1, tvThread2, tvThread3;
    private Button btnThread1, btnThread2, btnThread3;
    private boolean isThread1Running = true;
    private boolean isThread2Running = true;
    private boolean isThread3Running = true;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvThread1 = findViewById(R.id.tvThread1);
        tvThread2 = findViewById(R.id.tvThread2);
        tvThread3 = findViewById(R.id.tvThread3);

        btnThread1 = findViewById(R.id.btnThread1);
        btnThread2 = findViewById(R.id.btnThread2);
        btnThread3 = findViewById(R.id.btnThread3);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        tvThread1.setText("Thread 1: " + msg.obj);
                        break;
                    case 2:
                        tvThread2.setText("Thread 2: " + msg.obj);
                        break;
                    case 3:
                        tvThread3.setText("Thread 3: " + msg.obj);
                        break;
                }
            }
        };

        // Start 3 threads after 2 seconds
        new android.os.Handler().postDelayed(this::startThreads, 2000);

        btnThread1.setOnClickListener(v -> isThread1Running = !isThread1Running);
        btnThread2.setOnClickListener(v -> isThread2Running = !isThread2Running);
        btnThread3.setOnClickListener(v -> isThread3Running = !isThread3Running);
    }

    private void startThreads() {
        // Thread 1: Generate random number between 50-100 every second
        new Thread(() -> {
            Random random = new Random();
            while (true) {
                if (isThread1Running) {
                    int number = random.nextInt(51) + 50;
                    Message msg = handler.obtainMessage(1, number);
                    handler.sendMessage(msg);
                }
                try {
                    Thread.sleep(1000); // 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Thread 2: Generate increasing odd numbers every 2.5 seconds
        new Thread(() -> {
            int oddNumber = 1;
            while (true) {
                if (isThread2Running) {
                    Message msg = handler.obtainMessage(2, oddNumber);
                    handler.sendMessage(msg);
                    oddNumber += 2; // Next odd number
                }
                try {
                    Thread.sleep(2500); // 2.5 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Thread 3: Generate increasing integers starting from 0 every 2 seconds
        new Thread(() -> {
            int number = 0;
            while (true) {
                if (isThread3Running) {
                    Message msg = handler.obtainMessage(3, number);
                    handler.sendMessage(msg);
                    number++; // Increment number
                }
                try {
                    Thread.sleep(2000); // 2 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}