package com.example.accessreminder.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class Reminder: BroadcastReceiver() {

/*
    private fun setAlarm(context:Context,  taskId:Int,   days:Int) {
        val calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days);
      val alarmManager =  context.getSystemService(Context.ALARM_SERVICE);
        val intent =  Intent(context, MainActivity.class)
        intent.setAction("com.example.ACTION_ALARM")
        intent.putExtra("task_id", taskId);
       val pendingIntent = PendingIntent.getBroadcast(
                context,
        taskId + MainActivity.ALARM_REQUEST_CODE_BASE,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
        );

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
*/
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && intent.getAction() != null &&
            intent.getAction().equals("com.example.ACTION_ALARM")) {

            val taskId = intent.getIntExtra("task_id", -1)
            if (taskId != -1) {
                // Trigger the event for the task
                // ... Implement your logic here

                // Set the alarm to repeat after three days
              //  setAlarm(context, taskId, 3);
            }
        }
    }

}