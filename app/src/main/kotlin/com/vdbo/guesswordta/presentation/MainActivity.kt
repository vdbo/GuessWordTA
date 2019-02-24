package com.vdbo.guesswordta.presentation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.vdbo.guesswordta.R
import com.vdbo.guesswordta.presentation.common.displaySize
import com.vdbo.guesswordta.presentation.common.setBackgroundColorRes
import com.vdbo.guesswordta.presentation.injection.ComponentKeeper

class MainActivity : AppCompatActivity() {

    private lateinit var container: FrameLayout
    private lateinit var progress: ProgressBar
    private lateinit var counter: TextView
    private lateinit var originalWord: TextView
    private lateinit var matchingWord: TextView
    private lateinit var match: MaterialButton
    private lateinit var notMatch: MaterialButton
    private lateinit var matchingWordPosition: Pair<Float, Float>
    private val matchingWordAnim: ObjectAnimator by lazy(LazyThreadSafetyMode.NONE) {
        ObjectAnimator.ofFloat(
            matchingWord,
            "translationY",
            0f,
            displaySize().second.toFloat() - match.height
        ).apply {
            interpolator = LinearInterpolator()
        }
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, ComponentKeeper.appComponent().viewModelFactory)
            .get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUi()
        setUpObserver()
    }

    private fun setUpUi() {
        container = findViewById(R.id.container)
        progress = findViewById(R.id.progress)
        counter = findViewById(R.id.counter)
        originalWord = findViewById(R.id.original_word)
        matchingWord = findViewById(R.id.matching_word)
        match = findViewById(R.id.match)
        notMatch = findViewById(R.id.not_match)

        matchingWordPosition = Pair(matchingWord.x, matchingWord.y)
        match.setOnClickListener { viewModel.matchWord() }
        notMatch.setOnClickListener { viewModel.notMatchWord() }
    }

    private fun setUpObserver() {
        viewModel.mainState.observe(this, Observer { state ->
            when (state) {
                MainState.Loading -> progress.isVisible = true
                MainState.GameIsReadyToStart -> {
                    progress.isVisible = false
                    viewModel.startGame()
                }
                is MainState.GuessWordNew -> {
                    matchingWord.isVisible = false
                    matchingWord.y = matchingWordPosition.second
                    matchingWordAnim.setDuration(state.answerDurationMs).start()
                }
                is MainState.GuessWordInProgress -> {
                    matchingWord.isVisible = true
                    originalWord.text = state.originalWord
                    matchingWord.text = state.matchingWord
                    counter.text = state.counter
                }
                is MainState.GuessWordMatchResult -> {
                    container.setBackgroundColorRes(state.background)
                }
                is MainState.GameEnd -> {
                    matchingWord.isVisible = false
                    counter.isVisible = false
                    originalWord.setText(state.message)
                    container.setBackgroundColorRes(state.background)
                }
            }.also { }
        })
    }

}
