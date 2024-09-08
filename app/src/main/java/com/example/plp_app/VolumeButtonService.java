package com.example.plp_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.VibrationEffect;
import androidx.core.app.NotificationCompat;

public class VolumeButtonService extends Service {
    private static final String CHANNEL_ID = "VolumeButtonServiceChannel";
    private static final int NOTIFICATION_ID = 1;
    private static final int DEFAULT_VOLUME = 50;
    private static final int CHECK_INTERVAL = 50; // milliseconds
    private Vibrator vibrator;
    private int counter;
    private AudioManager audioManager;
    private PowerManager.WakeLock wakeLock;
    private Handler handler;
    private int maxVolume;

    @Override
    public void onCreate() {
        super.onCreate();
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        if(vibrator == null) {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "VolumeButtonService::WakeLock");
        counter = 0;
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Volume Button Counter")
                .setContentText("Listening for volume button presses")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();

        startForeground(NOTIFICATION_ID, notification);

        wakeLock.acquire();

        setVolumeToDefault();

        startVolumeCheck();

        return START_STICKY;
    }

    private void startVolumeCheck() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                checkVolumeChange();
                handler.postDelayed(this, CHECK_INTERVAL);
            }
        });
    }

    private void checkVolumeChange() {
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int defaultVolumeLevel = (DEFAULT_VOLUME * maxVolume) / 100;

        if (currentVolume > defaultVolumeLevel) {
            incrementCounter();
            setVolumeToDefault();
        } else if (currentVolume < defaultVolumeLevel) {
            decrementCounter();
            setVolumeToDefault();
        }
    }

    private void playVolumeAdjustSound() {
        if (vibrator == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(100);
        }
    }
    private void setVolumeToDefault() {
        int defaultVolumeLevel = (DEFAULT_VOLUME * maxVolume) / 100;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, defaultVolumeLevel, 0);
    }

    private void incrementCounter() {
//        counter++;
        playVolumeAdjustSound();
        broadcastUpdate(1);
    }

    private void decrementCounter() {
//        counter--;
        playVolumeAdjustSound();
        broadcastUpdate(-1);
    }

    private void broadcastUpdate(int counter) {
        Intent intent = new Intent("COUNTER_UPDATED");
        intent.putExtra("counter", counter);
        sendBroadcast(intent);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Volume Button Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (wakeLock.isHeld()) {
            wakeLock.release();
        }
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}