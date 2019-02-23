package com.vdbo.guesswordta.presentation.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider


class ViewModelFactory @Inject constructor(
    private val providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider = providers[modelClass]
            ?: providers.asIterable().firstOrNull { modelClass.isAssignableFrom(modelClass) }?.value
            ?: throw java.lang.IllegalArgumentException("There is no provider for class $modelClass")

        return provider.get() as T
    }

}