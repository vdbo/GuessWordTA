package com.vdbo.guesswordta

import android.app.Application
import com.vdbo.guesswordta.presentation.injection.ComponentKeeper

class GApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ComponentKeeper.init(this)
    }

}