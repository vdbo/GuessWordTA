package com.vdbo.guesswordta.presentation

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

sealed class MainState {

    object Loading : MainState()

    object GameIsReadyToStart : MainState()

    data class GuessWordInProgress(
        val originalWord: String,
        val matchingWord: String,
        val matchingWordPosition: Long,
        val counter: String
    ) : MainState()

    data class UserAnswer(
        @ColorRes val background: Int,
        @StringRes val message: Int
    ) : MainState()

    data class GameEnd(
        val message: String,
        val backgroundColor: String
    ) : MainState()

}