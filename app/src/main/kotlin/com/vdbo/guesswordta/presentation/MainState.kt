package com.vdbo.guesswordta.presentation

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

sealed class MainState {

    object Loading : MainState()

    object GameIsReadyToStart : MainState()

    data class GuessWordNew(
        val answerDurationMs: Long
    ) : MainState()

    data class GuessWordInProgress(
        val originalWord: String,
        val matchingWord: String,
        val counter: String
    ) : MainState()

    data class GuessWordMatchResult(
        @ColorRes val background: Int
    ) : MainState()

    data class GameEnd(
        @ColorRes val background: Int,
        @StringRes val message: Int
    ) : MainState()

}