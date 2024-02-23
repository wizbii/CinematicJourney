package com.wizbii.cinematic.journey

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.wizbii.cinematic.journey.core.koinAppDeclaration
import org.koin.core.context.startKoin

lateinit var appContext: Context
    private set

var mainActivity: MainActivity? = null
    private set

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks())

        startKoin(koinAppDeclaration)
    }

    inner class ActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

        override fun onActivityCreated(
            activity: Activity,
            savedInstanceState: Bundle?,
        ) {
            // NOOP
        }

        override fun onActivityStarted(activity: Activity) {
            if (activity is MainActivity) {
                mainActivity = activity
            }
        }

        override fun onActivityResumed(activity: Activity) {
            // NOOP
        }

        override fun onActivityPaused(activity: Activity) {
            // NOOP
        }

        override fun onActivityStopped(activity: Activity) {
            if (activity == mainActivity) {
                mainActivity = null
            }
        }

        override fun onActivitySaveInstanceState(
            activity: Activity,
            outState: Bundle,
        ) {
            // NOOP
        }

        override fun onActivityDestroyed(activity: Activity) {
            // NOOP
        }

    }

}
