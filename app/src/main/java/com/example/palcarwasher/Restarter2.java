package com.example.palcarwasher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
public class Restarter2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //  Log.i("Broadcast Listened", "Service tried to stop");
        //  Toast.makeText(context, "Service restarted", Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, YourService2.class));
        }
//        else {
//            context.startService(new Intent(context, YourService2.class));
//        }
    }
}