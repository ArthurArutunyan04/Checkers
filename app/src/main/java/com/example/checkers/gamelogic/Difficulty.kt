package com.example.checkers.gamelogic

import androidx.annotation.StringRes
import com.example.checkers.R

enum class Difficulty(@StringRes val displayNameRes: Int) {
    DUEL(R.string.difficulty_duel),
    EASY(R.string.difficulty_easy),
    MEDIUM(R.string.difficulty_medium),
    HARD(R.string.difficulty_hard)
}