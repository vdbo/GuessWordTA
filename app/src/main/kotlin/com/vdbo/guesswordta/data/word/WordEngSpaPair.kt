package com.vdbo.guesswordta.data.word

import com.google.gson.annotations.SerializedName

data class WordEngSpaPair(
    @SerializedName("text_eng") val original: String,
    @SerializedName("text_spa") val translated: String
)