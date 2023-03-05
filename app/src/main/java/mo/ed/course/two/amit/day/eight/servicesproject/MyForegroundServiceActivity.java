package mo.ed.course.two.amit.day.eight.servicesproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import mo.ed.course.two.amit.day.eight.servicesproject.databinding.ActivityMyForegroundServiceBinding;
import mo.ed.course.two.amit.day.eight.servicesproject.services.MyForegroundService;

public class MyForegroundServiceActivity extends AppCompatActivity {

    private Intent foregroundIntent;
    private ActivityMyForegroundServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_foreground_service);

        foregroundIntent=new Intent(MyForegroundServiceActivity.this, MyForegroundService.class);

        binding.btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // main thread
                new Thread(new Runnable() { // background
                    @Override
                    public void run() {
                        startService(); // Background Thread
                    }
                }).start();
//                startService(); // main thread - > block ui / ANR ( Application Not responding - Ui freezing
            }
        });

        binding.btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService_();
            }
        });
    }

    private void stopService_(){
        stopService(foregroundIntent);
    }

    private void startService(){
        foregroundIntent.putExtra(Constants.FOREGROUND_SERVICE_INTENT,"Foreground Service is running ...");
        ContextCompat.startForegroundService(MyForegroundServiceActivity.this, foregroundIntent);
    }
}