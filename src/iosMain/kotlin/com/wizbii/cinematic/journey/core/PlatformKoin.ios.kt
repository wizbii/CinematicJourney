package com.wizbii.cinematic.journey.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.wizbii.cinematic.journey.data.Database
import okio.Path.Companion.toPath
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.URLByAppendingPathComponent

actual fun platformDataModule() = module {

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.createWithPath {
            val documentDirectory: NSURL? =
                NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
            documentDirectory
                ?.URLByAppendingPathComponent("cinematic-journey.preferences_pb")
                ?.path
                ?.toPath()
                ?: error("Failed to retrieve document directory for preferences file")
        }
    }

    single<SqlDriver> {
        NativeSqliteDriver(Database.Schema, "cinematic-journey.db")
    }

}
