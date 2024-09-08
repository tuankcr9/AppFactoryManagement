package com.example.plp_app;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class CounterAppActivity extends AppCompatActivity {
    private TextView counterTextView;
    private CounterUpdateReceiver counterUpdateReceiver;
    private AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_app);

        counterTextView = findViewById(R.id.counterTextView);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // Đăng ký receiver để nhận cập nhật từ service
        counterUpdateReceiver = new CounterUpdateReceiver();
        IntentFilter filter = new IntentFilter("COUNTER_UPDATED");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(counterUpdateReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        }

        // Khởi động VolumeButtonService
        Intent serviceIntent = new Intent(this, VolumeButtonService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }

        Button btn_VolumeUp = findViewById(R.id.btn_volumeUp);
        Button btn_VolumeDown = findViewById(R.id.btn_volumeDown);

        btn_VolumeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0);
            }
        });

        btn_VolumeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(counterUpdateReceiver);
    }

    private void updateCounterTextView(int value) {
        counterTextView.setText(String.valueOf(value));
    }

    private class CounterUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("COUNTER_UPDATED")) {
                int counter = intent.getIntExtra("counter", 0);
                updateCounterTextView(counter);
            }
        }
    }
}