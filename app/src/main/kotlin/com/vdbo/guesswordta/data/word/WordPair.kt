package com.vdbo.guesswordta.data.word

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WordPair(
    @SerializedName("text_eng") val original: String,
    @SerializedName("text_spa") val translated: String,
    @Expose val group: LangPair
)