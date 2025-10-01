package com.example.checkers.gamelogic



import androidx.annotation.StringRes
import com.example.checkers.R

enum class PlayerColor(@StringRes val displayNameRes: Int, val pieceResource: Int) {
    WHITE(R.string.forces_of_light, R.drawable.white_def),
    BLACK(R.string.forces_of_darkness, R.drawable.black_def)
}