package com.example.tbcacademy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentGameBinding
import com.example.tbcacademy.utils.extensions.showSnackBar

class GameFragment : BaseFragment<FragmentGameBinding>() {

    private lateinit var board: Array<Array<String>>
    private lateinit var buttons: Array<Array<ImageButton>>
    private var currentPlayer = "X"
    private var boardSize = 3

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGameBinding {
        return FragmentGameBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        boardSize = arguments?.getInt("boardSize") ?: 3

        val gridLayout = binding.gridLayout
        gridLayout.removeAllViews()
        gridLayout.rowCount = boardSize
        gridLayout.columnCount = boardSize

        board = Array(boardSize) { Array(boardSize) { "" } }
        buttons = Array(boardSize) { i ->
            Array(boardSize) { j ->
                ImageButton(requireContext())
            }
        }

        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                val button = ImageButton(requireContext())
                button.setBackgroundResource(R.drawable.button_background)
                button.scaleType = ImageView.ScaleType.FIT_CENTER
                button.setPadding(16, 16, 16, 16)
                button.elevation = 4f

                val params = GridLayout.LayoutParams(
                    GridLayout.spec(i, 1f),
                    GridLayout.spec(j, 1f)
                ).apply {
                    width = 0
                    height = 0
                    setMargins(12, 12, 12, 12)
                }

                button.layoutParams = params
                button.setOnClickListener {
                    if (board[i][j].isEmpty()) {
                        board[i][j] = currentPlayer
                        button.setImageResource(
                            if (currentPlayer == "X") R.drawable.ic_x else R.drawable.ic_circle
                        )
                        if (checkWin(i, j)) {
                            binding.root.showSnackBar("Player $currentPlayer wins!")
                            disableAllButtons()
                        } else if (isBoardFull()) {
                            binding.root.showSnackBar("Draw")
                        } else {
                            currentPlayer = if (currentPlayer == "X") "O" else "X"
                        }
                    }
                }
                buttons[i][j] = button
                gridLayout.addView(button, params)
            }
        }
    }

    private fun checkWin(row: Int, col: Int): Boolean {
        val player = board[row][col]
        if ((0 until boardSize).all { board[row][it] == player }) return true
        if ((0 until boardSize).all { board[it][col] == player }) return true
        if (row == col && (0 until boardSize).all { board[it][it] == player }) return true
        if (row + col == boardSize - 1 && (0 until boardSize).all { board[it][boardSize - 1 - it] == player }) return true
        return false
    }

    private fun isBoardFull(): Boolean = board.all { row -> row.all { it.isNotEmpty() } }

    private fun disableAllButtons() {
        for (i in 0 until boardSize)
            for (j in 0 until boardSize)
                buttons[i][j].isEnabled = false
    }
}