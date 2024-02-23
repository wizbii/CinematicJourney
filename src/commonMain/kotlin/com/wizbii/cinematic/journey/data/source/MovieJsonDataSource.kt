package com.wizbii.cinematic.journey.data.source

import com.wizbii.cinematic.journey.domain.entity.JsonMovie
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import com.wizbii.cinematicjourney.generated.resources.Res
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okio.internal.commonToUtf8String
import org.jetbrains.compose.resources.ExperimentalResourceApi

class MovieJsonDataSource {

    @OptIn(ExperimentalResourceApi::class)
    suspend fun readJsonMovies(universeId: UniverseId): List<JsonMovie> =
        withContext(Dispatchers.IO) {
            Json.decodeFromString<List<JsonMovie>>(
                string = Res.readBytes("files/movies/${universeId}.json").commonToUtf8String(),
            )
        }

}
