package com.example.accessreminder.database.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class RemindTask : RealmObject() {
    @PrimaryKey
    var packageName: String = ""
    var name: String = ""
    var lastRemind: Long = 0
    var grantedPermission = RealmList<String>()

}


