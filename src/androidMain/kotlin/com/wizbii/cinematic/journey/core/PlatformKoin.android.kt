package com.wizbii.cinematic.journey.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.wizbii.cinematic.journey.appContext
import com.wizbii.cinematic.journey.data.Database
import okio.Path.Companion.toPath
import org.koin.dsl.module

actual fun platformDataModule() = module {

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.createWithPath {
            appContext.filesDir.resolve("cinematic-journey.preferences_pb").absolutePath.toPath()
        }
    }

    single<SqlDriver> {
        AndroidSqliteDriver(Database.Schema, appContext, "cinematic-journey.db")
    }

}
