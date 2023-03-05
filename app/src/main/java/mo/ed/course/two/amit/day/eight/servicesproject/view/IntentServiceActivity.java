package mo.ed.course.two.amit.day.eight.servicesproject.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import mo.ed.course.two.amit.day.eight.servicesproject.R;
import mo.ed.course.two.amit.day.eight.servicesproject.databinding.ActivityIntentServiceBinding;
import mo.ed.course.two.amit.day.eight.servicesproject.services.MyWebIntentService;

public class IntentServiceActivity extends AppCompatActivity {

    private ActivityIntentServiceBinding binding;
    private MyWebServiceBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intent_service);

        // define Broadcast Receiver

        IntentFilter intentFilter= new IntentFilter(MyWebServiceBroadcastReceiver.PROCESS_RESPONSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);

        receiver = new MyWebServiceBroadcastReceiver();
        registerReceiver(receiver, intentFilter);


        binding.sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent msgIntent=new Intent(IntentServiceActivity.this, MyWebIntentService.class);
                //1
                msgIntent.putExtra(MyWebIntentService.REQUEST_STRING, "https://www.google.com");
                startService(msgIntent);
                //2
                msgIntent.putExtra(MyWebIntentService.REQUEST_STRING,"https://www.youtube.com");
                startService(msgIntent);
                //3
                msgIntent.putExtra(MyWebIntentService.REQUEST_STRING,"https://www.linkedin.com");
                startService(msgIntent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public class MyWebServiceBroadcastReceiver extends BroadcastReceiver{

        public static final String PROCESS_RESPONSE = "com.as400samplecode.intent.action.PROCESS_RESPONSE";

        @Override
        public void onReceive(Context context, Intent intent) {
            String responseString = intent.getStringExtra(MyWebIntentService.RESPONSE_STRING);
            String responseMessage = intent.getStringExtra(MyWebIntentService.RESPONSE_MESSAGE);

            binding.response.setText(responseString);
            binding.myWebView.getSettings().setJavaScriptEnabled(true);
            try {
                if (responseMessage!=null){
                    binding.myWebView.loadData(URLEncoder.encode(responseMessage, "utf-8").replaceAll("\\+", " "), "text/html", "UTF-8");
                }else {
                    Log.e("NullValue", "responseMessage: "+responseMessage);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}