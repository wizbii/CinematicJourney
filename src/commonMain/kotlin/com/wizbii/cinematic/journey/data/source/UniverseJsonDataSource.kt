package com.wizbii.cinematic.journey.data.source

import com.wizbii.cinematic.journey.domain.entity.JsonUniverse
import com.wizbii.cinematicjourney.generated.resources.Res
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okio.internal.commonToUtf8String
import org.jetbrains.compose.resources.ExperimentalResourceApi

class UniverseJsonDataSource {

    @OptIn(ExperimentalResourceApi::class)
    suspend fun readJsonUniverses(): List<JsonUniverse> =
        withContext(Dispatchers.IO) {
            Json.decodeFromString<List<JsonUniverse>>(
                string = Res.readBytes("files/universes.json").commonToUtf8String(),
            )
        }

}
