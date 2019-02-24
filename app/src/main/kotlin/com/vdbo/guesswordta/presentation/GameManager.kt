package com.vdbo.guesswordta.presentation

import com.vdbo.guesswordta.data.word.WordEngSpaPair
import com.vdbo.guesswordta.presentation.model.AnswerResult
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

class GameManager @Inject constructor() {

    companion object {
        private const val WORDS_PER_GAME = 10
        private const val ATTEMPTS_PER_GAME = 3
        private const val WORD_OCCURRENCE = 3
        const val SECONDS_FOR_ANSWER = 5L
        const val START_COUNTER = SECONDS_FOR_ANSWER
    }

    private val disposables = CompositeDisposable()
    private val _wordsChanges = PublishSubject.create<WordEngSpaPair>()
    private val _answerChanges = PublishSubject.create<AnswerResult>()
    private val _gameWinChanges = PublishSubject.create<Boolean>()
    val wordsChanges: Observable<WordEngSpaPair> = _wordsChanges
    val answerChanges: Observable<AnswerResult> = _answerChanges
    val gameWinChanges: Observable<Boolean> = _gameWinChanges
    val counterChanges: Observable<Long> =
        Observable.intervalRange(0, SECONDS_FOR_ANSWER + 1, 0, 1, TimeUnit.SECONDS)
            .map { count -> SECONDS_FOR_ANSWER - count }
            .takeUntil(_wordsChanges)
            .repeatWhen { it.takeUntil(_gameWinChanges) }
            .subscribeOn(Schedulers.io())

    private lateinit var words: List<WordEngSpaPair>
    private var wordsToAsk = LinkedList<WordEngSpaPair>()
    private var attempts = ATTEMPTS_PER_GAME

    fun init(words: List<WordEngSpaPair>) {
        this.words = words
    }

    fun startGame() {
        stopGame()

        repeat(WORDS_PER_GAME) {
            val wordsBlock = ArrayList<WordEngSpaPair>(WORD_OCCURRENCE)
            val mainWordPair = words.random()

            with(wordsBlock) {
                add(mainWordPair)
                for (i in 2..WORD_OCCURRENCE) {
                    val fakeWordPair = WordEngSpaPair(
                        mainWordPair.original,
                        words.random().translated
                    )
                    add(fakeWordPair)
                }
                shuffle()
            }

            wordsToAsk.addAll(wordsBlock)
        }

        _wordsChanges.onNext(wordsToAsk.poll())
    }

    fun submitMatch(wordToSubmit: WordEngSpaPair) {
        if (words.contains(wordToSubmit)) {
            wordsToAsk.removeAll { wordToAsk -> wordToAsk.original == wordToSubmit.original }
            correctAnswer()
        } else {
            wrongAnswer()
        }
    }

    fun submitNotMatch(wordToSubmit: WordEngSpaPair) {
        if (!words.contains(wordToSubmit)) {
            correctAnswer()
        } else {
            wordsToAsk.removeAll { wordToAsk -> wordToAsk.original == wordToSubmit.original }
            wrongAnswer()
        }
    }

    fun stopGame() {
        wordsToAsk.clear()
        attempts = ATTEMPTS_PER_GAME
        disposables.clear()
    }

    private fun correctAnswer() {
        if (wordsToAsk.size == 0) {
            _gameWinChanges.onNext(true)
        } else {
            _answerChanges.onNext(AnswerResult.CORRECT)
            _wordsChanges.onNext(wordsToAsk.poll())
        }
    }

    private fun wrongAnswer() {
        attempts--

        if (attempts < 1) {
            _gameWinChanges.onNext(false)
            return
        }
        if (wordsToAsk.size == 0) {
            _gameWinChanges.onNext(true)
        } else {
            _answerChanges.onNext(AnswerResult.WRONG)
            _wordsChanges.onNext(wordsToAsk.poll())
        }
    }

}