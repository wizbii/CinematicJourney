package com.wizbii.cinematic.journey.core

import co.touchlab.kermit.Logger
import co.touchlab.kermit.koin.KermitKoinLogger
import com.wizbii.cinematic.journey.data.Database
import com.wizbii.cinematic.journey.data.DatabaseWithAdapters
import com.wizbii.cinematic.journey.data.repository.DefaultJsonFilesRepository
import com.wizbii.cinematic.journey.data.repository.DefaultMovieRepository
import com.wizbii.cinematic.journey.data.repository.DefaultPreferencesRepository
import com.wizbii.cinematic.journey.data.repository.DefaultTmdbRepository
import com.wizbii.cinematic.journey.data.repository.DefaultUniverseRepository
import com.wizbii.cinematic.journey.data.source.MovieJsonDataSource
import com.wizbii.cinematic.journey.data.source.PreferencesLocalDataSource
import com.wizbii.cinematic.journey.data.source.TmdbApiDataSource
import com.wizbii.cinematic.journey.data.source.UniverseJsonDataSource
import com.wizbii.cinematic.journey.domain.repository.JsonFilesRepository
import com.wizbii.cinematic.journey.domain.repository.MovieRepository
import com.wizbii.cinematic.journey.domain.repository.PreferencesRepository
import com.wizbii.cinematic.journey.domain.repository.TmdbRepository
import com.wizbii.cinematic.journey.domain.repository.UniverseRepository
import com.wizbii.cinematic.journey.domain.use.case.GetMovieBackdropUrlForWidthUseCase
import com.wizbii.cinematic.journey.domain.use.case.GetMoviePosterUrlForWidthUseCase
import com.wizbii.cinematic.journey.domain.use.case.ObserveMovieUseCase
import com.wizbii.cinematic.journey.domain.use.case.ObserveMoviesForUniverseUseCase
import com.wizbii.cinematic.journey.domain.use.case.ObserveUniverseUseCase
import com.wizbii.cinematic.journey.domain.use.case.ObserveUniversesUseCase
import com.wizbii.cinematic.journey.domain.use.case.SetMovieWatchedUseCase
import com.wizbii.cinematic.journey.domain.use.case.dark.mode.AlignPlatformDarkModeUseCase
import com.wizbii.cinematic.journey.domain.use.case.dark.mode.GetDarkModePreferenceUseCase
import com.wizbii.cinematic.journey.domain.use.case.dark.mode.ObserveDarkModePreferenceUseCase
import com.wizbii.cinematic.journey.domain.use.case.dark.mode.SetDarkModePreferenceUseCase
import com.wizbii.cinematic.journey.domain.use.case.dark.mode.ToggleDarkModeUseCase
import com.wizbii.cinematic.journey.domain.use.case.start.EnhanceMoviesWithTmdbUseCase
import com.wizbii.cinematic.journey.domain.use.case.start.InitializeOrUpdateDatabaseUseCase
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val koinAppDeclaration: KoinAppDeclaration = {
    logger(KermitKoinLogger(Logger.withTag("Koin")))
    modules(mainModule())
}

private fun mainModule() = module {
    includes(
        dataModule(),
        domainModule(),
    )
}

private fun dataModule() = module {

    includes(platformDataModule())

    single { DatabaseWithAdapters(get()) }
    single { get<Database>().moviesQueries }
    single { get<Database>().prerequisitesQueries }
    single { get<Database>().tmdbMoviesQueries }
    single { get<Database>().universesQueries }

    single { PreferencesLocalDataSource(get()) }
    single { MovieJsonDataSource() }
    single { TmdbApiDataSource() }
    single { UniverseJsonDataSource() }

    single<JsonFilesRepository> { DefaultJsonFilesRepository(get(), get()) }
    single<MovieRepository> { DefaultMovieRepository(get(), get()) }
    single<PreferencesRepository> { DefaultPreferencesRepository(get()) }
    single<TmdbRepository> { DefaultTmdbRepository(get(), get()) }
    single<UniverseRepository> { DefaultUniverseRepository(get(), get()) }

}

private fun domainModule() = module {

    factory { EnhanceMoviesWithTmdbUseCase(get(), get()) }
    factory { InitializeOrUpdateDatabaseUseCase(get(), get(), get()) }

    factory { AlignPlatformDarkModeUseCase(get()) }
    factory { GetDarkModePreferenceUseCase(get()) }
    factory { ObserveDarkModePreferenceUseCase(get()) }
    factory { SetDarkModePreferenceUseCase(get()) }
    factory { ToggleDarkModeUseCase(get()) }

    factory { GetMovieBackdropUrlForWidthUseCase(get()) }
    factory { GetMoviePosterUrlForWidthUseCase(get()) }
    factory { ObserveMovieUseCase(get(), get()) }
    factory { ObserveMoviesForUniverseUseCase(get(), get()) }
    factory { ObserveUniverseUseCase(get()) }
    factory { ObserveUniversesUseCase(get()) }
    factory { SetMovieWatchedUseCase(get()) }

}
