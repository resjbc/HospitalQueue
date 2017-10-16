package hospital.hospitalqueue;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Idres on 16/10/2560.
 */

public class Setting {

    Context context;
    MediaPlayer mp;
    Vibrator v;
    NotificationCompat.Builder builder;
    NotificationManager notificationManager;

     Setting(Context context) {
        this.context = context;
    }



      void PlaySound(){

        mp = MediaPlayer.create(context, R.raw.sound);
        if(mp.isPlaying()) mp.stop();
        mp.start();
          Log.d("Dd","play");

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.release();
               // v.cancel();
            }
        });
    }

      void PlayVibrate(){
        long[] pattern = {0,1000, 1000};
        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(pattern, -1);
    }

     void NotifyWarn(){

        Intent i = new Intent(context,ShowQueue.class);
        //i.putExtra("mainTG", "true");
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent =  PendingIntent.getActivity(context,0, i,PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_main3);

        builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setTicker("Friend Travel มีข้อความใหม่")
                .setSmallIcon(R.drawable.tray)
                .setLargeIcon(bitmap)
                .setContentTitle("Friend Travel")
                .setContentText("ถึงจุดนัดหมายในระยะที่ได้เลือกไว้เเล้ว")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Notification notification = builder.build();

        //notification.defaults |= Notification.DEFAULT_SOUND;

        notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.sound);
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        notification.ledARGB = Color.BLUE;

        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notification.ledOnMS = 100;
        notification.ledOffMS = 100;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;



        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(5, notification);
//notificationManager.cancel(idNoti);


    }






}
