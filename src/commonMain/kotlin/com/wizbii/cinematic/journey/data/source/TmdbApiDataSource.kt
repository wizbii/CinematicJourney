package com.wizbii.cinematic.journey.data.source

import com.wizbii.cinematic.journey.TMDB_API_KEY
import com.wizbii.cinematic.journey.data.source.TmdbApiDataSource.TmdbImageType.BACKDROP
import com.wizbii.cinematic.journey.data.source.TmdbApiDataSource.TmdbImageType.POSTER
import com.wizbii.cinematic.journey.domain.entity.TmdbMovieId
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import kotlin.time.Duration.Companion.seconds
import co.touchlab.kermit.Logger.Companion as KermitLogger

class TmdbApiDataSource {

    companion object {

        private const val API_BASE_URL_V3 = "https://api.themoviedb.org/3/"

        private const val TARGET_MAX_REQUEST_PER_SECOND = 30

    }

    private var configuration: TmdbConfiguration? = null

    private val configurationLock = Mutex()

    private val http = HttpClient {

        expectSuccess = true

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                @OptIn(ExperimentalSerializationApi::class)
                namingStrategy = JsonNamingStrategy.SnakeCase
            })
        }

        install(DefaultRequest) {
            url(API_BASE_URL_V3)
            url { parameters.append("api_key", TMDB_API_KEY) }
        }

        install(HttpRequestRetry) {
            exponentialDelay()
            retryIf(5) { _, response ->
                val responseCode = response.status.value
                responseCode in 500..599 || responseCode == 429
            }
        }

        install(Logging) {
            level = LogLevel.INFO
            logger = object : Logger {

                private val log = KermitLogger.withTag("TmdbClient")

                override fun log(message: String) =
                    log.i(message)

            }
        }

    }

    private val httpLock = Mutex()

    private var nextRequestMinStartInstant = Instant.DISTANT_PAST

    suspend fun getBackdropUrlForWidth(backdropPath: String, width: Int): String =
        getImageUrlForWidth(backdropPath, width, BACKDROP)

    suspend fun getMovieDetails(tmdbMovieId: TmdbMovieId, language: String): TmdbMovieDetails =
        try {
            throttle {
                http.get("movie/$tmdbMovieId") {
                    parameter("language", language)
                }.body()
            }
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.NotFound) {
                throw TmdbMovieNotFoundException(tmdbMovieId)
            } else {
                throw e
            }
        }

    suspend fun getPosterUrlForWidth(posterPath: String, width: Int): String =
        getImageUrlForWidth(posterPath, width, POSTER)

    private suspend fun fetchConfiguration(): TmdbConfiguration =
        throttle {
            http.get("configuration").body()
        }

    private suspend fun getConfiguration(): TmdbConfiguration =
        configurationLock.withLock {
            configuration ?: fetchConfiguration().also { configuration = it }
        }

    private suspend fun getImageUrlForWidth(path: String, width: Int, type: TmdbImageType): String {
        val configuration = getConfiguration()
        val sizes = when (type) {
            BACKDROP -> configuration.images.backdropSizes
            POSTER   -> configuration.images.posterSizes
        }
        val size = sizes
            .filter { it.startsWith("w") }
            .firstOrNull { it.drop(1).toInt() >= width }
            ?: "original"
        val imageUrl = buildString {
            append(configuration.images.secureBaseUrl)
            append(size)
            append(path)
        }
        return imageUrl
    }

    private suspend fun <T> throttle(request: suspend () -> T): T =
        httpLock.withLock {
            val now = Clock.System.now()
            if (now < nextRequestMinStartInstant) {
                delay(nextRequestMinStartInstant - now)
            }
            return try {
                withContext(Dispatchers.IO) {
                    request()
                }
            } finally {
                nextRequestMinStartInstant = now + 1.seconds / TARGET_MAX_REQUEST_PER_SECOND
            }
        }

    @Serializable
    data class TmdbMovieDetails(
        val backdropPath: String?,
        val id: TmdbMovieId,
        val overview: String?,
        val posterPath: String?,
        val releaseDate: LocalDate,
        val runtime: Int,
        val tagline: String?,
        val title: String,
    )

    class TmdbMovieNotFoundException(id: TmdbMovieId) :
        Exception("No TMDB movie with id $id found")

    @Serializable
    private data class TmdbConfiguration(
        val images: Images,
    ) {

        @Serializable
        data class Images(
            val backdropSizes: List<String>,
            val posterSizes: List<String>,
            val secureBaseUrl: String,
        )

    }

    private enum class TmdbImageType {
        BACKDROP,
        POSTER,
    }

}
