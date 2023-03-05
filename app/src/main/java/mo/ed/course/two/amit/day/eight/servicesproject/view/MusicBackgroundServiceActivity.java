package mo.ed.course.two.amit.day.eight.servicesproject.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import mo.ed.course.two.amit.day.eight.servicesproject.Constants;
import mo.ed.course.two.amit.day.eight.servicesproject.R;
import mo.ed.course.two.amit.day.eight.servicesproject.databinding.ActivityMusicMyBackgroundServiceBinding;
import mo.ed.course.two.amit.day.eight.servicesproject.services.MusicBackgroundService;

public class MusicBackgroundServiceActivity extends AppCompatActivity {

    private ActivityMusicMyBackgroundServiceBinding binding;
    boolean isPlaying= false;
    private Intent musicBackgroundService;
    private boolean isRunning;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Constants.IS_MUSIC_RUNNING, isRunning);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_music_my_background_service);
        if (savedInstanceState!=null && savedInstanceState.containsKey(Constants.IS_MUSIC_RUNNING)){
            isRunning=savedInstanceState.getBoolean(Constants.IS_MUSIC_RUNNING);
            if (!isRunning){
                binding.btnMusic.setImageResource(R.drawable.ic_play);
            }else {
                binding.btnMusic.setImageResource(R.drawable.ic_stop);
            }
        }
        musicBackgroundService=new Intent(MusicBackgroundServiceActivity.this, MusicBackgroundService.class);
        binding.btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = isMyServiceRunning(MusicBackgroundService.class);
                if (!isRunning){
                    binding.btnMusic.setImageResource(R.drawable.ic_stop);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            startService(musicBackgroundService);
                            isRunning=true;
                        }
                    }).start();
                }else {
                    binding.btnMusic.setImageResource(R.drawable.ic_play);
                    stopService(musicBackgroundService);
                    isRunning=false;
                }
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}