package com.example.accessreminder.activities.main

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.accessreminder.constants.AppConst
import com.example.accessreminder.database.model.RemindTask
import io.realm.Realm
import io.realm.RealmList

class MainViewModel:ViewModel() {
    val  realm = Realm.getDefaultInstance()
    val remindTasks by lazy {  MutableLiveData<List<RemindTask>>()}

    private val GRANTED= "Granted"
   private fun addApp( remindTask: RemindTask){
        realm.executeTransaction {
              it.insertOrUpdate(remindTask )
        }

    }
    fun retrieveApp(packageManager:PackageManager  ){
        realm.executeTransaction {
            it.deleteAll()
        }
        val installedApps = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS)
        val userInstalledApps = installedApps.filter { !isSystemApp(it.applicationInfo) }
        for (app in userInstalledApps) {
            val appName = app.applicationInfo.loadLabel(packageManager).toString()
            val permissions = app.requestedPermissions
            val remindTask = RemindTask()
            remindTask.packageName =app.packageName
            remindTask.name =appName
            if (permissions != null) {
                for (permission in permissions) {
                    val permissionStatus = checkPermissionStatus(packageManager,app.packageName, permission)
                    if(permissionStatus==GRANTED) {
                        remindTask.grantedPermission.add(permission)
                    }
                }
            }
            addApp(remindTask)
                Log.d(AppConst.TAG, " insert "+appName)
        }

    }

    fun retrieveApp(  ){
        val apps = realm.where(RemindTask::class.java).findAll()
        Log.d(AppConst.TAG, " Display "+apps.size.toString())
        remindTasks.value=     realm.copyFromRealm(apps)

    }

    private fun isSystemApp(applicationInfo: ApplicationInfo): Boolean {

        return   applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }
    private fun checkPermissionStatus(packageManager: PackageManager, packageName: String, permission: String): String {
        val permissionStatus = packageManager.checkPermission(permission, packageName)
        return if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            GRANTED
        } else {
            "Denied"
        }
    }

}