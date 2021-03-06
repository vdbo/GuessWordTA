package com.vdbo.guesswordta.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vdbo.guesswordta.R
import com.vdbo.guesswordta.data.word.WordEngSpaPair
import com.vdbo.guesswordta.data.word.WordRepository
import com.vdbo.guesswordta.presentation.model.AnswerResult
import com.vdbo.mytaxita.presentation.common.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.withLatestFrom
import javax.inject.Inject

class MainViewModel @Inject constructor(
    wordRepository: WordRepository,
    private val gameManager: GameManager
) : BaseViewModel() {

    private val _mainState = MutableLiveData<MainState>()
    val mainState: LiveData<MainState> = _mainState

    init {
        _mainState.value = MainState.Loading
        wordRepository.getWords()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    gameManager.init(it)
                    _mainState.value = MainState.GameIsReadyToStart
                },
                onError = { Log.e("MainViewModel", it.localizedMessage) }
            )
            .addTo(disposables)
    }

    fun startGame() {
        observeCounter()
        observeAnswer()
        observeGameResult()
        gameManager.startGame()
    }

    fun matchWord() {
        mainState.value.let { state ->
            if (state is MainState.GuessWordInProgress) {
                gameManager.submitMatch(
                    WordEngSpaPair(state.originalWord, state.matchingWord)
                )
            }
        }
    }

    fun notMatchWord() {
        mainState.value.let { state ->
            if (state is MainState.GuessWordInProgress) {
                gameManager.submitNotMatch(
                    WordEngSpaPair(state.originalWord, state.matchingWord)
                )
            }
        }
    }

    private fun observeCounter() {
        gameManager.counterChanges.withLatestFrom(gameManager.wordsChanges)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { (count, word) ->
                    if (count == 0L) {
                        gameManager.submitMatch(word)
                        return@subscribeBy
                    }
                    if (count == GameManager.START_COUNTER) {
                        _mainState.value = MainState.GuessWordNew(
                            GameManager.SECONDS_FOR_ANSWER * 1000
                        )
                    }
                    _mainState.value = MainState.GuessWordInProgress(
                        word.original,
                        word.translated,
                        count.toString()
                    )
                },
                onError = { Log.e("MainViewModel", it.localizedMessage) }
            )
            .addTo(disposables)
    }

    private fun observeAnswer() {
        gameManager.answerChanges
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { answer ->
                    when (answer) {
                        AnswerResult.CORRECT -> _mainState.value =
                            MainState.GuessWordMatchResult(R.color.correct)
                        AnswerResult.WRONG -> _mainState.value =
                            MainState.GuessWordMatchResult(R.color.wrong)
                        else -> IllegalArgumentException("Retrieved wrong answer: $answer")
                    }
                },
                onError = { Log.e("MainViewModel", it.localizedMessage) }
            )
            .addTo(disposables)
    }

    private fun observeGameResult() {
        gameManager.gameWinChanges
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { isWin ->
                    if (isWin) {
                        _mainState.value = MainState.GameEnd(R.color.correct, R.string.win)
                    } else {
                        _mainState.value = MainState.GameEnd(R.color.wrong, R.string.loose)
                    }
                },
                onError = { Log.e("MainViewModel", it.localizedMessage) }
            )
            .addTo(disposables)
    }

}