package com.example.checkers.gamelogic

import com.example.checkers.R

enum class PlayerColor(val displayName: String, val pieceResource: Int) {
    WHITE("Белые", R.drawable.white_def),
    BLACK("Черные", R.drawable.black_def)
}