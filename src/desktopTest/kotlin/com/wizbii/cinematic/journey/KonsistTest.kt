package com.wizbii.cinematic.journey

import com.lemonappdev.konsist.api.KoModifier.COMPANION
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.declaration.KoFunctionDeclaration
import com.lemonappdev.konsist.api.declaration.KoPropertyDeclaration
import com.lemonappdev.konsist.api.ext.list.indexOfFirstInstance
import com.lemonappdev.konsist.api.ext.list.indexOfLastInstance
import com.lemonappdev.konsist.api.ext.list.modifierprovider.withValueModifier
import com.lemonappdev.konsist.api.ext.list.primaryConstructors
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.ext.list.withPackage
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlin.test.Test

class KonsistTest {

    @Test
    fun `Clean Architecture dependencies are respected`() {
        Konsist
            .scopeFromProduction()
            .assertArchitecture {
                val core = Layer("Core", "com.wizbii.cinematic.journey.core..")
                val data = Layer("Data", "com.wizbii.cinematic.journey.data..")
                val domain = Layer("Domain", "com.wizbii.cinematic.journey.domain..")
                val presentation = Layer("Presentation", "com.wizbii.cinematic.journey.presentation..")

                domain.dependsOnNothing()
                data.dependsOn(domain)
                presentation.dependsOn(domain)
                core.dependsOn(data, domain, presentation)
            }
    }

    @Test
    fun `Companion object is first declaration in class`() {
        Konsist
            .scopeFromProject()
            .classes()
            .assertTrue { clazz ->
                val companion = clazz
                    .objects(includeNested = false)
                    .lastOrNull { it.hasModifier(COMPANION) }
                    ?: return@assertTrue true
                val firstDeclaration = clazz
                    .declarations(includeNested = false, includeLocal = false)
                    .first { declaration ->
                        declaration !is KoPropertyDeclaration || !declaration.isConstructorDefined
                    }
                firstDeclaration == companion
            }
    }

    @Test
    fun `DataSources have name ending with 'DataSource'`() {
        Konsist
            .scopeFromProduction()
            .files
            .withPackage("com.wizbii.cinematic.journey.data.source..")
            .assertTrue { file ->
                file.hasNameEndingWith("DataSource")
            }
    }

    @Test
    fun `Data Repositories have names ending with 'Repository'`() {
        Konsist
            .scopeFromProduction()
            .files
            .withPackage("com.wizbii.cinematic.journey.data.repository..")
            .assertTrue { file ->
                file.hasNameEndingWith("Repository")
            }
    }

    @Test
    fun `Domain Repositories have names ending with 'Repository'`() {
        Konsist
            .scopeFromProduction()
            .files
            .withPackage("com.wizbii.cinematic.journey.domain.repository..")
            .assertTrue { file ->
                file.hasNameEndingWith("Repository")
            }
    }

    @Test
    fun `Locals have names starting with 'Local'`() {
        Konsist
            .scopeFromProduction()
            .files
            .withPackage("com.wizbii.cinematic.journey.presentation.local..")
            .assertTrue { file ->
                file.hasNameStartingWith("Local")
            }
    }

    @Test
    fun `Properties are declared before functions`() {
        Konsist
            .scopeFromProject()
            .classes()
            .assertTrue { clazz ->
                val lastPropertyIndex = clazz
                    .declarations(includeNested = false, includeLocal = false)
                    .indexOfLastInstance<KoPropertyDeclaration>()
                    .takeIf { it >= 0 }
                    ?: return@assertTrue true
                val firstFunctionIndex = clazz
                    .declarations(includeNested = false, includeLocal = false)
                    .indexOfFirstInstance<KoFunctionDeclaration>()
                    .takeIf { it >= 0 }
                    ?: return@assertTrue true
                lastPropertyIndex < firstFunctionIndex
            }
    }

    @Test
    fun `UseCases should be in domain use case package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue { clazz ->
                clazz.resideInPackage("com.wizbii.cinematic.journey.domain.use.case..")
            }
    }

    @Test
    fun `UseCases should have single 'public operator' method named 'invoke' and nothing else`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue { clazz ->
                val hasInvokeOperatorFunction = clazz.hasFunction { function ->
                    with(function) {
                        name == "invoke" && hasPublicOrDefaultModifier && hasOperatorModifier
                    }
                }
                val publicFunctionsCount = clazz.countFunctions { it.hasPublicOrDefaultModifier }
                val publicPropertiesCount = clazz.countProperties { it.hasPublicOrDefaultModifier }
                hasInvokeOperatorFunction && publicFunctionsCount == 1 && publicPropertiesCount == 0
            }
    }

    @Test
    fun `Value classes have parameter named 'value'`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withValueModifier()
            .primaryConstructors
            .assertTrue { constructor ->
                constructor.hasParameterWithName("value")
            }
    }

    @Test
    fun `Wildcard imports are forbidden`() {
        Konsist
            .scopeFromProject()
            .imports
            .assertFalse { import ->
                import.isWildcard
            }
    }

}
