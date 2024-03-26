package com.wizbii.cinematic.journey.data

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.adapter.primitive.FloatColumnAdapter
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.wizbii.cinematic.journey.domain.entity.MovieId
import com.wizbii.cinematic.journey.domain.entity.TmdbMovieId
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

@Suppress("FunctionName")
fun DatabaseWithAdapters(driver: SqlDriver): Database =
    Database(
        driver = driver,
        movieRecordAdapter = MovieRecord.Adapter(
            idAdapter = MovieIdAdapter,
            releaseDateAdapter = LocalDateAdapter,
            tmdbIdAdapter = TmdbMovieIdAdapter,
            universeIdAdapter = UniverseIdAdapter,
        ),
        prerequisiteRecordAdapter = PrerequisiteRecord.Adapter(
            movieIdAdapter = MovieIdAdapter,
            prerequisiteMovieIdAdapter = MovieIdAdapter,
        ),
        tmdbMovieRecordAdapter = TmdbMovieRecord.Adapter(
            idAdapter = TmdbMovieIdAdapter,
            fetchDateAdapter = InstantAdapter,
            releaseDateAdapter = LocalDateAdapter,
            runtimeAdapter = IntColumnAdapter,
            voteAverageAdapter = FloatColumnAdapter,
        ),
        universeRecordAdapter = UniverseRecord.Adapter(
            idAdapter = UniverseIdAdapter,
        )
    )

private object InstantAdapter : ColumnAdapter<Instant, Long> {
    override fun decode(databaseValue: Long) = Instant.fromEpochMilliseconds(databaseValue)
    override fun encode(value: Instant) = value.toEpochMilliseconds()
}

private object LocalDateAdapter : ColumnAdapter<LocalDate, String> {
    override fun decode(databaseValue: String) = LocalDate.parse(databaseValue)
    override fun encode(value: LocalDate) = value.toString()
}

private object MovieIdAdapter : ColumnAdapter<MovieId, String> {
    override fun decode(databaseValue: String) = MovieId(databaseValue)
    override fun encode(value: MovieId) = value.value
}

private object TmdbMovieIdAdapter : ColumnAdapter<TmdbMovieId, Long> {
    override fun decode(databaseValue: Long) = TmdbMovieId(databaseValue.toInt())
    override fun encode(value: TmdbMovieId) = value.value.toLong()
}

private object UniverseIdAdapter : ColumnAdapter<UniverseId, String> {
    override fun decode(databaseValue: String) = UniverseId(databaseValue)
    override fun encode(value: UniverseId) = value.value
}
