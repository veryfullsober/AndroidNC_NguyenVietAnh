package com.example.baith1_bai3;

import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private NumberPicker numberPicker;
    private Button btnStart, btnStop;
    private Handler handler = new Handler();
    private Vibrator vibrator;
    private boolean isAlarmRunning = false;
    private Runnable alarmRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        timePicker = findViewById(R.id.timePicker);
        numberPicker = findViewById(R.id.numberPicker);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        timePicker.setIs24HourView(true);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(60);
        btnStart.setOnClickListener(v -> startAlarm());
        btnStop.setOnClickListener(v -> stopAlarm());
    }
    private void startAlarm() {
        if (isAlarmRunning) {
            Toast.makeText(this, "Báo thức đang rung!", Toast.LENGTH_SHORT).show();
            return;
        }
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        int duration = numberPicker.getValue();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        long currentTime = System.currentTimeMillis();
        long selectedTime = calendar.getTimeInMillis();
        long delay = selectedTime - currentTime;
        if (delay < 0) {
            Toast.makeText(this, "Thời gian bạn chọn đã trôi qua!", Toast.LENGTH_SHORT).show();
            return;
        }
        handler.postDelayed(alarmRunnable = new Runnable() {
            @Override
            public void run() {
                isAlarmRunning = true;
                long[] vibrationPattern = {0, 1000, 1000};
                vibrator.vibrate(vibrationPattern, 0);
                handler.postDelayed(this, duration * 60 * 1000);
            }
        }, delay);
        Toast.makeText(this, "Cài đặt báo thức " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
    }
    private void stopAlarm() {
        if (!isAlarmRunning) {
            Toast.makeText(this, "Báo thức không sẵn sàng!", Toast.LENGTH_SHORT).show();
            return;
        }
        handler.removeCallbacks(alarmRunnable);
        vibrator.cancel();
        isAlarmRunning = false;
        Toast.makeText(this, "Báo thức đã dừng!", Toast.LENGTH_SHORT).show();
    }
}