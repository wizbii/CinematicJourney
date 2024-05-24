package com.wizbii.cinematic.journey.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.wizbii.cinematic.journey.data.MoviesQueries
import com.wizbii.cinematic.journey.data.UniverseRecord
import com.wizbii.cinematic.journey.data.UniversesQueries
import com.wizbii.cinematic.journey.domain.entity.JsonUniverse
import com.wizbii.cinematic.journey.domain.entity.Universe
import com.wizbii.cinematic.journey.domain.entity.UniverseId
import com.wizbii.cinematic.journey.domain.repository.UniverseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

class DefaultUniverseRepository(
    private val moviesQueries: MoviesQueries,
    private val universeQueries: UniversesQueries,
) : UniverseRepository {

    override suspend fun createOrUpdateUniverses(universes: List<JsonUniverse>) {
        withContext(Dispatchers.IO) {
            universeQueries.transaction {
                universes.forEach { universe ->
                    universeQueries.createUniverseIfNotExist(
                        id = universe.id,
                        isFinished = universe.isFinished,
                        isDisabled = universe.isDisabled,
                    )
                }
            }
        }
    }

    override fun getUniverse(id: UniverseId): Flow<Universe> =
        universeQueries
            .readUniverseById(id)
            .asFlow()
            .mapToOne(Dispatchers.IO)
            .combine(getUniverseDateRange(id), ::buildUniverse)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getUniverses(): Flow<Set<Universe>> =
        universeQueries
            .readAllUniverses()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { records ->
                records.map { record ->
                    flowOf(record).combine(getUniverseDateRange(record.id), ::buildUniverse)
                }
            }
            .flatMapLatest { universeFlows ->
                if (universeFlows.isEmpty()) {
                    flowOf(emptySet())
                } else {
                    combine(universeFlows, Array<Universe>::toSet)
                }
            }

    private fun buildUniverse(
        record: UniverseRecord,
        dates: Pair<LocalDate, LocalDate>,
    ) = Universe(
        id = record.id,
        startDate = dates.first,
        endDate = if (record.isFinished) dates.second else null,
    )

    private fun getUniverseDateRange(id: UniverseId): Flow<Pair<LocalDate, LocalDate>> =
        moviesQueries
            .readUniverseDateRange(id)
            .asFlow()
            .mapToOne(Dispatchers.IO)
            .mapNotNull { record ->
                Pair(
                    record.startDate ?: return@mapNotNull null,
                    record.endDate ?: return@mapNotNull null,
                )
            }

}
