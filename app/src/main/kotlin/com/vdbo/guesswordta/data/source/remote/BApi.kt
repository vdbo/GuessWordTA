package com.vdbo.guesswordta.data.source.remote

import com.vdbo.guesswordta.data.word.WordPair
import io.reactivex.Single
import retrofit2.http.GET

interface BApi {

    @GET("7ac6cdb4bf5e032f4c737aaafe659b33/raw/baa9fe0d586082d85db71f346e2b039c580c5804/")
    fun getWords(): Single<List<WordPair>>

}