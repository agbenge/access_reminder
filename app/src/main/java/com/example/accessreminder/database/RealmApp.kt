package com.example.accessreminder.database

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmApp: Application() {
   private var config: RealmConfiguration?=null;
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        config= RealmConfiguration.Builder()
            .name("access_reminder.db").schemaVersion(0)
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()
        config.let {
            Realm.setDefaultConfiguration(it)
        }

    }
}