package com.example.day_12

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.util.*

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent : Intent) {
        val id = intent.getIntExtra(Utils.ID_INTENT_KEY, 0)
        val label = intent.getStringExtra(Utils.LABEL_INTENT_KEY)
        val description = intent.getStringExtra(Utils.DESC_INTENT_KEY)

        if (label != null && description != null) {
            showAlarmNotification(context, label, description, id)
        }
    }

    fun setOneTimeAlarm(context: Context, time: String, date: String, label: String, desc: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(Utils.LABEL_INTENT_KEY, label)
        intent.putExtra(Utils.DESC_INTENT_KEY, desc)
        intent.putExtra(Utils.ID_INTENT_KEY, Utils.ONE_TIME_ID)

        Log.e("ONE TIME", "$date $time")
        val dateArray = date.split("/").toTypedArray()
        val timeArray = time.split(":").toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[0]))
        calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1]) - 1)
        calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[2]))
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            Utils.ONE_TIME_ID,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Toast.makeText(context, "One time alarm set up", Toast.LENGTH_SHORT).show()
    }

    fun setCustomTimeAlarm(context: Context, time: String, date: String, label: String, desc: String, repeatTime: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(Utils.LABEL_INTENT_KEY, label)
        intent.putExtra(Utils.DESC_INTENT_KEY, desc)
        intent.putExtra(Utils.ID_INTENT_KEY, Utils.REPEATING_TIME_ID)

        Log.e("ONE TIME", "$date $time")
        val dateArray = date.split("/").toTypedArray()
        val timeArray = time.split(":").toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[0]))
        calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1]) - 1)
        calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[2]))
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            Utils.REPEATING_TIME_ID,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY * repeatTime,
            pendingIntent)

        Toast.makeText(context, "Repeating time alarm set up", Toast.LENGTH_SHORT).show()
    }

    private fun showAlarmNotification(
        context: Context,
        label: String,
        description: String,
        notifId: Int
    ) {

        val channelId = "Channel_1"
        val channelName = "AlarmManager channel"

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.baseline_access_time_24)
            .setContentTitle(label)
            .setContentText(description)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(channelId)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManagerCompat.notify(notifId, notification)

    }
}