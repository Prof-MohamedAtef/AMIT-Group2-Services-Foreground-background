package mo.ed.course.two.amit.day.eight.servicesproject.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import mo.ed.course.two.amit.day.eight.servicesproject.R;

public class MusicBackgroundService extends Service {
    private MediaPlayer musicPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        musicPlayer = MediaPlayer.create(this, R.raw.tamali);
        musicPlayer.setLooping(false);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        musicPlayer.start();
        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("BackgroundUnbind","Unbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        musicPlayer.stop();
        Log.e("Background","Destroyed");
    }
}
