package com.vdbo.guesswordta.data.word

import com.vdbo.guesswordta.data.source.remote.BApi
import dagger.Reusable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@Reusable class WordRepository @Inject constructor(
    private val bApi: BApi
) {

    fun getWords(): Single<List<WordPair>> {
        return bApi.getWords()
            .subscribeOn(Schedulers.io())
    }

}