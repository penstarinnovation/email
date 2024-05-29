package com.fsck.k9.hanvon.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

class MyDataStore {

    companion object {
        lateinit var dataStore: DataStore<Preferences>
        fun init(context: Context) {
//            dataStore = PreferenceDataStoreFactory.create(context)
        }

    }


}

