package com.vdbo.guesswordta.presentation

import com.vdbo.guesswordta.data.word.WordEngSpaPair
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

class GameManager @Inject constructor() {

    companion object {
        const val WORDS_PER_GAME = 10
        const val ATTEMPTS_PER_GAME = 3
        const val WORD_OCCURRENCE = 3
        const val SECONDS_FOR_ANSWER = 5L
    }

    private val disposables = CompositeDisposable()
    private val _wordsChanges = PublishSubject.create<WordEngSpaPair>()
    private var _gameEndChanges = PublishSubject.create<Unit>()
    private var _answerChanges = PublishSubject.create<AnswerResult>()
    val wordsChanges: Observable<WordEngSpaPair> = _wordsChanges
    val answerChanges: Observable<AnswerResult> = _answerChanges
    val counterChanges: Observable<Long> =
        Observable.intervalRange(0, SECONDS_FOR_ANSWER + 1, 0, 1, TimeUnit.SECONDS)
            .map { count -> SECONDS_FOR_ANSWER - count }
            .takeUntil(_wordsChanges)
            .repeatWhen { it.takeUntil(_gameEndChanges) }

    private lateinit var words: List<WordEngSpaPair>
    private var wordsToAsk = LinkedList<WordEngSpaPair>()
    private var attempts = ATTEMPTS_PER_GAME

    fun init(words: List<WordEngSpaPair>) {
        this.words = words
    }

    fun startGame() {
        stopGame()

        for (i in 0 until WORDS_PER_GAME) {
            val wordsBlock = ArrayList<WordEngSpaPair>(WORD_OCCURRENCE)
            val mainWordPair = words.random()

            wordsBlock.add(mainWordPair)
            for (i in 2..WORD_OCCURRENCE) {
                val fakeWordPair = WordEngSpaPair(
                    mainWordPair.original,
                    words.random().translated
                )
                wordsBlock.add(fakeWordPair)
            }
            wordsBlock.shuffle()

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

    fun pauseGame() {
        //todo: pause game correctly
    }

    fun stopGame() {
        wordsToAsk.clear()
        attempts = ATTEMPTS_PER_GAME
        disposables.clear()
    }

    private fun correctAnswer() {
        if (wordsToAsk.size == 0) {
            //todo: user won
            return
        }
        _answerChanges.onNext(AnswerResult.CORRECT)
        _wordsChanges.onNext(wordsToAsk.poll())
    }

    private fun wrongAnswer() {
        attempts--
        if (attempts < 1) _gameEndChanges.onNext(Unit)
        _answerChanges.onNext(AnswerResult.WRONG)
        _wordsChanges.onNext(wordsToAsk.poll())
    }

}

enum class AnswerResult {
    CORRECT, WRONG
}