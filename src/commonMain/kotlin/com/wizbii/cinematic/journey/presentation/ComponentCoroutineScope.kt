package com.wizbii.cinematic.journey.presentation

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

fun ComponentContext.componentCoroutineScope(): CoroutineScope {
    val componentName = this::class.simpleName?.removePrefix("Default") ?: "Component"
    val log = Logger.withTag("${componentName}CoroutineScope")
    val context: CoroutineContext = listOf<CoroutineContext>(
        CoroutineExceptionHandler { _, err ->
            log.e(err) { "Uncaught error in $componentName coroutine scope: ${err.message}" }
        },
        CoroutineName("${componentName}Coroutine"),
        Dispatchers.Main.immediate,
        SupervisorJob(),
    ).reduce(CoroutineContext::plus)
    val scope = CoroutineScope(context)
    lifecycle.doOnDestroy(scope::cancel)
    return scope

}
