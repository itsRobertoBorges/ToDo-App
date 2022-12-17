package net.penguincoders.doit;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationUtils extends ContextWrapper
{

    private NotificationManager _notificationManager;
    private Context _context;

    public NotificationUtils(Context base)
    {
        super(base);
        _context = base;
        createChannel();
    }

    public NotificationCompat.Builder setNotification(String title, String body)
    {
        return new NotificationCompat.Builder(this, "notification")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(_context.getResources(),
                        R.mipmap.ic_launcher))
                .setContentTitle("ToDo List")
                .setContentText("Don't forget to keep track of your tasks!")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
    }

    private void createChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("notification", "timeline_notification", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(channel);
        }
    }

    public NotificationManager getManager()
    {
        if(_notificationManager == null)
        {
            _notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return _notificationManager;
    }

    public void setReminder(long timeInMillis)
    {
        Intent _intent = new Intent(_context, ReminderBroadcast.class);
        PendingIntent _pendingIntent = PendingIntent.getBroadcast(_context, 0, _intent, 0);

        AlarmManager _alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        _alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, _pendingIntent);
    }

}