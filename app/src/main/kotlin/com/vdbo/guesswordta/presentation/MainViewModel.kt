package com.vdbo.guesswordta.presentation

import android.util.Log
import com.vdbo.guesswordta.data.word.WordRepository
import com.vdbo.mytaxita.presentation.common.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val wordRepository: WordRepository
) : BaseViewModel() {

    fun getWords() {

        wordRepository.getWords()
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    val a = 0
                },
                onError = { Log.e("MainViewModel", it.localizedMessage) }
            )
            .addTo(disposables)

    }

}