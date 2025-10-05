package com.example.checkers.gamelogic

import android.content.Context
import com.example.checkers.R
import kotlin.math.abs

object GameLogic {
    fun indexToChessNotation(index: Int): String {
        val row = 8 - (index / 8)
        val col = 'A' + (index % 8)
        return "$col$row"
    }

    fun chessNotationToIndex(notation: String): Int {
        if (notation.length != 2) return -1
        val colChar = notation[0].uppercaseChar()
        val rowChar = notation[1]

        if (colChar !in 'A'..'H' || rowChar !in '1'..'8') return -1

        val col = colChar - 'A'
        val row = 8 - (rowChar - '0')
        return row * 8 + col
    }

    fun getPieceColor(res: Int): PlayerColor? {
        return when (res) {
            R.drawable.white_def, R.drawable.white_win -> PlayerColor.WHITE
            R.drawable.black_def, R.drawable.black_win -> PlayerColor.BLACK
            else -> null
        }
    }

    fun hasMandatoryCaptures(state: GameState): Boolean {
        val playerPieces = state.board.filterKeys { index ->
            val piece = state.board[index] ?: return@filterKeys false
            getPieceColor(piece) == state.currentPlayer
        }.keys

        for (fromIndex in playerPieces) {
            val fromRow = fromIndex / 8
            val fromCol = fromIndex % 8
            val fromPiece = state.board[fromIndex] ?: continue
            val isKing = fromPiece == R.drawable.white_win || fromPiece == R.drawable.black_win
            val directions = listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)

            for ((drow, dcol) in directions) {
                for (dist in 2..7) {
                    val toRow = fromRow + drow * dist
                    val toCol = fromCol + dcol * dist
                    if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) break
                    val toIndex = toRow * 8 + toCol
                    if (!state.isValidCell(toIndex)) continue
                    if (isValidCapture(state, fromIndex, toIndex)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun isValidMove(state: GameState, fromIndex: Int, toIndex: Int): Boolean {
        if (!state.isValidCell(fromIndex) || !state.isValidCell(toIndex)) return false
        val fromPiece = state.board[fromIndex] ?: return false
        val toPiece = state.board[toIndex]
        if (toPiece != null) return false

        val fromRow = fromIndex / 8
        val fromCol = fromIndex % 8
        val toRow = toIndex / 8
        val toCol = toIndex % 8

        val rowDiff = toRow - fromRow
        val colDiff = toCol - fromCol
        if (abs(rowDiff) != abs(colDiff)) return false

        val pieceColor = getPieceColor(fromPiece) ?: return false
        val isWhite = pieceColor == PlayerColor.WHITE
        val isKing = fromPiece == R.drawable.white_win || fromPiece == R.drawable.black_win

        if (!isKing && abs(rowDiff) != 1) {
            return false
        }

        if (abs(rowDiff) > 1) {
            val stepRow = if (rowDiff > 0) 1 else -1
            val stepCol = if (colDiff > 0) 1 else -1
            var currentRow = fromRow + stepRow
            var currentCol = fromCol + stepCol

            while (currentRow != toRow && currentCol != toCol) {
                val currentIndex = currentRow * 8 + currentCol
                if (!state.isValidCell(currentIndex) || state.board[currentIndex] != null) {
                    return false
                }
                currentRow += stepRow
                currentCol += stepCol
            }
        }

        val validDirection = if (isKing) true else if (isWhite) rowDiff < 0 else rowDiff > 0
        return validDirection
    }

    fun isValidCapture(state: GameState, fromIndex: Int, toIndex: Int): Boolean {
        if (!state.isValidCell(fromIndex) || !state.isValidCell(toIndex)) return false
        val fromPiece = state.board[fromIndex] ?: return false
        val toPiece = state.board[toIndex]
        if (toPiece != null) return false

        val fromRow = fromIndex / 8
        val fromCol = fromIndex % 8
        val toRow = toIndex / 8
        val toCol = toIndex % 8

        val rowDiff = toRow - fromRow
        val colDiff = toCol - fromCol
        if (abs(rowDiff) != abs(colDiff)) return false

        val pieceColor = getPieceColor(fromPiece) ?: return false
        val isKing = fromPiece == R.drawable.white_win || fromPiece == R.drawable.black_win

        val stepRow = if (rowDiff > 0) 1 else -1
        val stepCol = if (colDiff > 0) 1 else -1
        var currentRow = fromRow + stepRow
        var currentCol = fromCol + stepCol
        var captured = false

        while (currentRow != toRow && currentCol != toCol) {
            val currentIndex = currentRow * 8 + currentCol
            if (!state.isValidCell(currentIndex)) {
                return false
            }
            val currentPiece = state.board[currentIndex]
            if (currentPiece != null) {
                val midColor = getPieceColor(currentPiece)
                if (midColor == pieceColor) {
                    return false
                }
                if (midColor != pieceColor && midColor != null) {
                    if (captured) {
                        return false
                    }
                    captured = true
                }
            }
            if (!isKing && abs(currentRow - fromRow) >= 2 && captured) {
                break
            }
            currentRow += stepRow
            currentCol += stepCol
        }

        val valid = if (isKing) {
            captured
        } else {
            captured && abs(rowDiff) == 2
        }
        return valid
    }

    fun getPossibleMoves(state: GameState, fromIndex: Int): List<Int> {
        val possibleMoves = mutableListOf<Int>()
        val fromPiece = state.board[fromIndex] ?: return possibleMoves
        val pieceColor = getPieceColor(fromPiece) ?: return possibleMoves
        if (pieceColor != state.currentPlayer) {
            return possibleMoves
        }

        val fromRow = fromIndex / 8
        val fromCol = fromIndex % 8
        val isWhite = pieceColor == PlayerColor.WHITE
        val isKing = fromPiece == R.drawable.white_win || fromPiece == R.drawable.black_win

        val hasGlobalCaptures = hasMandatoryCaptures(state)

        val captureDirections = listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)
        for ((drow, dcol) in captureDirections) {
            val maxDist = if (isKing) 7 else 2
            for (dist in 2..maxDist) {
                val toRow = fromRow + drow * dist
                val toCol = fromCol + dcol * dist
                if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) break
                val toIndex = toRow * 8 + toCol
                if (!state.isValidCell(toIndex)) continue
                if (isValidCapture(state, fromIndex, toIndex)) {
                    possibleMoves.add(toIndex)
                }
            }
        }

        if (!hasGlobalCaptures) {
            val moveDirections = if (isKing) {
                listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)
            } else if (isWhite) {
                listOf(-1 to -1, -1 to 1)
            } else {
                listOf(1 to -1, 1 to 1)
            }

            for ((drow, dcol) in moveDirections) {
                val maxDist = if (isKing) 7 else 1
                for (dist in 1..maxDist) {
                    val toRow = fromRow + drow * dist
                    val toCol = fromCol + dcol * dist
                    if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) break
                    val toIndex = toRow * 8 + toCol
                    if (!state.isValidCell(toIndex)) continue
                    if (state.board[toIndex] != null) break
                    if (isValidMove(state, fromIndex, toIndex)) {
                        possibleMoves.add(toIndex)
                    }
                }
            }
        }

        return possibleMoves.distinct()
    }

    fun performMove(state: GameState, fromIndex: Int, toIndex: Int): Pair<Boolean, AnimatedPiece?> {
        val hasCaptures = hasMandatoryCaptures(state)
        val isCapture = isValidCapture(state, fromIndex, toIndex)
        val isSimpleMove = isValidMove(state, fromIndex, toIndex)

        println("Performing move: from=$fromIndex, to=$toIndex, hasCaptures=$hasCaptures, isCapture=$isCapture, isSimpleMove=$isSimpleMove")

        if (hasCaptures && !isCapture) {
            println("Move rejected: captures are mandatory")
            return false to null
        }
        if (!isSimpleMove && !isCapture) {
            println("Move rejected: invalid move or capture")
            return false to null
        }

        val piece = state.board[fromIndex] ?: return false to null
        val fromRow = fromIndex / 8
        val fromCol = fromIndex % 8
        val toRow = toIndex / 8
        val toCol = toIndex % 8

        val pieceColor = getPieceColor(piece) ?: return false to null
        val colorName = if (pieceColor == PlayerColor.WHITE) state.context.getString(R.string.forces_of_light) else state.context.getString(R.string.forces_of_darkness)

        val fromNotation = indexToChessNotation(fromIndex)
        val toNotation = indexToChessNotation(toIndex)

        val moveLog = if (isCapture) {
            state.context.getString(R.string.capture_move_format, colorName, fromNotation, toNotation)
        } else {
            state.context.getString(R.string.move_log_format, colorName, fromNotation, toNotation)
        }
        state.addLog(moveLog)
        println("Move logged: $moveLog")

        val animatedPiece = AnimatedPiece(
            pieceRes = piece,
            fromIndex = fromIndex,
            toIndex = toIndex,
            isCapture = isCapture,
            onAnimationEnd = {
                println("Executing move logic after animation: from=$fromIndex, to=$toIndex")
                state.board[toIndex] = piece
                state.board.remove(fromIndex)

                if (isCapture) {
                    val rowDiff = toRow - fromRow
                    val colDiff = toCol - fromCol
                    val stepRow = if (rowDiff > 0) 1 else -1
                    val stepCol = if (colDiff > 0) 1 else -1
                    var currentRow = fromRow + stepRow
                    var currentCol = fromCol + stepCol
                    while (currentRow != toRow && currentCol != toCol) {
                        val currentIndex = currentRow * 8 + currentCol
                        val currentPiece = state.board[currentIndex]
                        if (currentPiece != null) {
                            val midColor = getPieceColor(currentPiece)
                            if (midColor != pieceColor) {
                                state.board.remove(currentIndex)
                                val capturedNotation = indexToChessNotation(currentIndex)
                                state.addLog(state.context.getString(R.string.piece_captured, capturedNotation))
                                println("Captured piece at $capturedNotation")
                                break
                            }
                        }
                        currentRow += stepRow
                        currentCol += stepCol
                    }
                }

                val isWhite = pieceColor == PlayerColor.WHITE
                val wasKing = piece == R.drawable.white_win || piece == R.drawable.black_win
                if (!wasKing && ((isWhite && toRow == 0) || (!isWhite && toRow == 7))) {
                    state.board[toIndex] = if (isWhite) R.drawable.white_win else R.drawable.black_win
                    state.addLog(state.context.getString(R.string.piece_promoted, colorName, toNotation))
                    println("Piece promoted at $toNotation")
                }

                if (isCapture) {
                    val furtherCaptures = getPossibleMoves(state, toIndex).filter { furtherIndex ->
                        abs((furtherIndex / 8) - toRow) >= 2 && isValidCapture(state, toIndex, furtherIndex)
                    }
                    if (furtherCaptures.isNotEmpty()) {
                        state.selectedCell = toIndex
                        println("Further captures available, keeping selectedCell at $toIndex")
                    } else {
                        state.selectedCell = null
                        state.switchPlayer()
                        println("No further captures, switching player")
                    }
                } else {
                    state.selectedCell = null
                    state.switchPlayer()
                    println("Simple move, switching player")
                }

                checkWinCondition(state)
            }
        )

        return true to animatedPiece
    }

    private fun checkWinCondition(state: GameState) {
        val whitePieces = state.board.values.count { getPieceColor(it) == PlayerColor.WHITE }
        val blackPieces = state.board.values.count { getPieceColor(it) == PlayerColor.BLACK }

        if (whitePieces == 0) {
            state.winner.value = PlayerColor.BLACK
            state.gameOver.value = true
            state.addLog(state.context.getString(R.string.forces_of_darkness_victory))
        } else if (blackPieces == 0) {
            state.winner.value = PlayerColor.WHITE
            state.gameOver.value = true
            state.addLog(state.context.getString(R.string.forces_of_light_victory))
        }
    }

    fun aiMove(state: GameState) {
        /*TODO*/
    }
}