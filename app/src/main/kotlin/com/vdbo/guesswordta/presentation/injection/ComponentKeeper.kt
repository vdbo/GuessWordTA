package com.vdbo.guesswordta.presentation.injection

import android.app.Application
import com.vdbo.guesswordta.AppComponent
import com.vdbo.guesswordta.DaggerAppComponent

object ComponentKeeper {

    private lateinit var app: Application
    private var appComponent: AppComponent? = null

    fun init(app: Application) {
        ComponentKeeper.app = app
    }

    fun appComponent(): AppComponent = appComponent ?: DaggerAppComponent.builder()
        .build()
        .also { appComponent = it }

}