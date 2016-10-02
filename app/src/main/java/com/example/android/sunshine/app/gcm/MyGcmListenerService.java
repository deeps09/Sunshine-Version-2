package com.example.android.sunshine.app.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.android.sunshine.app.MainActivity;
import com.example.android.sunshine.app.R;
import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Deepesh_Gupta1 on 10/02/2016.
 */

public class MyGcmListenerService extends GcmListenerService {


    private static final String TAG = "MyGcmListenerService";

    private static final String EXTRA_DATA = "data";
    private static final String EXTRA_WEATHER = "weather";
    private static final String EXTRA_LOCATION = "location";

    public static final int NOTIFICATION_ID = 1;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        //Toast.makeText(this, "Processing Notification!", Toast.LENGTH_SHORT).show();

        for (String key: data.keySet() ){
            Log.i("Bundle Data Keys ", key + "\n" );
            Log.i("Bundle Values ", data.getString(key) + "\n" );
        }

        if (!data.isEmpty()) {
            String senderId = getString(R.string.gcm_defaultSenderId);
            if (senderId.length() == 0) {
                Toast.makeText(this, "This sender ID needs to be set", Toast.LENGTH_SHORT).show();
            }

            if ((senderId).equals(from)){
                try {
                    /*JSONObject jsonObject = new JSONObject(data.getString(EXTRA_DATA));
                    String weather = jsonObject.getString(EXTRA_WEATHER);
                    String location = jsonObject.getString(EXTRA_LOCATION);*/

                    //JSONObject jsonObject = new JSONObject(data.getString(EXTRA_DATA));
                    String weather = data.getString(EXTRA_WEATHER);
                    String location = data.getString(EXTRA_LOCATION);

                    String alert = String.format(getString(R.string.gcm_weather_alert),weather,location);
                    sendNotification(alert);
                } catch (Exception e){
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    private void sendNotification (String message){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        Bitmap largeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.art_storm);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.art_clear)
                .setLargeIcon(largeIcon)
                .setContentTitle("Weather Alert")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());


    }
}
