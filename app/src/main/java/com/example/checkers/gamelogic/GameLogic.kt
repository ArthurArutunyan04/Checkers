package com.example.checkers.gamelogic

import com.example.checkers.R
import kotlin.math.abs

object GameLogic {
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

        val valid = if (isKing) true else if (isWhite) rowDiff < 0 else rowDiff > 0
        return valid
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

        val directions = if (isKing) {
            listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)
        } else {
            listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)
        }

        for ((drow, dcol) in directions) {
            for (dist in 2..7) {
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
                for (dist in 1..7) {
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

    fun performMove(state: GameState, fromIndex: Int, toIndex: Int): Boolean {
        val hasCaptures = hasMandatoryCaptures(state)
        val isCapture = isValidCapture(state, fromIndex, toIndex)
        val isSimpleMove = isValidMove(state, fromIndex, toIndex)

        if (hasCaptures && !isCapture) {
            return false
        }
        if (!isSimpleMove && !isCapture) {
            return false
        }

        val piece = state.board[fromIndex] ?: return false
        val fromRow = fromIndex / 8
        val fromCol = fromIndex % 8
        val toRow = toIndex / 8
        val toCol = toIndex % 8

        val pieceColor = getPieceColor(piece) ?: return false
        val colorName = if (pieceColor == PlayerColor.WHITE) "Белые" else "Чёрные"
        state.addLog("$colorName: $fromIndex → $toIndex${if (isCapture) " (удаление)" else ""}")

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
            state.addLog("$colorName фигура на $toIndex стала дамкой")
        }

        if (isCapture) {
            val furtherCaptures = getPossibleMoves(state, toIndex).filter { furtherIndex ->
                abs((furtherIndex / 8) - toRow) >= 2 && isValidCapture(state, toIndex, furtherIndex)
            }
            if (furtherCaptures.isNotEmpty()) {
                state.selectedCell = toIndex
                return true
            }
        }

        state.selectedCell = null
        state.switchPlayer()
        checkWinCondition(state)
        return true
    }

    fun aiMove(state: GameState) {
        if (state.selectedDifficulty == Difficulty.DUEL || state.gameOver.value) return

        val aiColor = if (state.playerColor == PlayerColor.WHITE) PlayerColor.BLACK else PlayerColor.WHITE
        val aiPieces = state.board.filterKeys { index ->
            getPieceColor(state.board[index] ?: 0) == aiColor
        }.keys

        val hasCaptures = hasMandatoryCaptures(state)

        var captureMoves = mutableListOf<Pair<Int, Int>>()
        var simpleMoves = mutableListOf<Pair<Int, Int>>()

        for (from in aiPieces) {
            val moves = getPossibleMoves(state, from)
            val captures = moves.filter { abs((it / 8) - (from / 8)) >= 2 }
            val simples = moves.filter { abs((it / 8) - (from / 8)) == 1 }
            captureMoves.addAll(captures.map { from to it })
            if (!hasCaptures) simpleMoves.addAll(simples.map { from to it })
        }

        val chosenMoves = if (captureMoves.isNotEmpty()) captureMoves else simpleMoves
        if (chosenMoves.isNotEmpty()) {
            val (from, to) = chosenMoves.random()
            performMove(state, from, to)
        }
    }

    private fun checkWinCondition(state: GameState) {
        val whitePieces = state.board.values.count { getPieceColor(it) == PlayerColor.WHITE }
        val blackPieces = state.board.values.count { getPieceColor(it) == PlayerColor.BLACK }

        if (whitePieces == 0) {
            state.winner.value = PlayerColor.BLACK
            state.gameOver.value = true
            state.addLog("Чёрные победили!")
        } else if (blackPieces == 0) {
            state.winner.value = PlayerColor.WHITE
            state.gameOver.value = true
            state.addLog("Белые победили!")
        }
    }
}