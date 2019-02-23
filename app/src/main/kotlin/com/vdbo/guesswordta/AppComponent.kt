package com.vdbo.guesswordta

import androidx.lifecycle.ViewModelProvider
import com.vdbo.guesswordta.data.source.remote.RetrofitModule
import com.vdbo.guesswordta.presentation.injection.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    val viewModelFactory: ViewModelProvider.Factory

}