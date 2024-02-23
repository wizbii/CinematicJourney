package com.wizbii.cinematic.journey.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.wizbii.cinematic.journey.data.Database
import okio.Path.Companion.toPath
import org.koin.dsl.module
import java.io.File

actual fun platformDataModule() = module {

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.createWithPath {
            val folderPath = System.getProperty("java.io.tmpdir")
            File(folderPath).resolve("cinematic-journey.preferences_pb").absolutePath.toPath()
        }
    }

    single<SqlDriver> {
        JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also { driver ->
            Database.Schema.create(driver)
        }
    }

}
