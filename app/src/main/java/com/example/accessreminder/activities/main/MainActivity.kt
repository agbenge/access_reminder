package com.example.accessreminder.activities.main

import android.Manifest
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.accessreminder.R
import com.example.accessreminder.database.model.RemindTask
import io.realm.Realm


class MainActivity : AppCompatActivity() {
    private lateinit var permissionStatusLayout: LinearLayout

    private val ALARM_ACTION = "com.example.ACTION_ALARM"
    private val ALARM_TASK_ID_EXTRA = "task_id"

    private val ALARM_REQUEST_CODE_BASE = 1000

    //private val taskList: List<Task>? = null
    private var alarmRequestCodeCounter = 0
    private val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
    )
    private var realm: Realm? = null
    lateinit var viewModel:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionStatusLayout = findViewById(R.id.permissionStatusLayout)

         viewModel= ViewModelProvider(this)[MainViewModel::class.java]




        realm = Realm.getDefaultInstance();
        requestPermissions()
        viewModel.retrieveApp(packageManager)
        viewModel.retrieveApp()
viewModel.remindTasks.observe(this){

            it.forEach() {
          val textView =TextView(this,  )
          textView.setPadding(8,8,8,50)
          textView.setText( it.name+
                  "\n"+it.grantedPermission.toList())
          permissionStatusLayout.addView(textView)
      }

}
    }




/*
    private fun triggerInitialEvent(task: Task) {
        // Trigger initial event for the task
        // ... Implement your logic here

        // Set the alarm to repeat after three days
        setAlarm(task.getId(), 3)
    }

    private fun setAlarm(taskId: Int, days: Int) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, days)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(ALARM_ACTION)
        intent.putExtra(ALARM_TASK_ID_EXTRA, taskId)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            alarmRequestCodeCounter++,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager[AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()] = pendingIntent
    }

    private fun removeAlarm(taskId: Int) {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(ALARM_ACTION)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            taskId + ALARM_REQUEST_CODE_BASE,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
        }
    }

    private fun removeTask(taskId: Int) {
        // Remove the task from the list
        // ... Implement your logic here

        // Remove the associated alarm
        removeAlarm(taskId)
    }

*/





































    val REQUEST_CODE_PERMISSIONS=0
    private fun requestPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {


            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), REQUEST_CODE_PERMISSIONS)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            for (i in permissions.indices) {
                val permission = permissions[i]
                val grantResult = grantResults[i]

                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, handle accordingly
                } else {
                    // Permission denied, handle accordingly
                }
            }
        }
    }


}