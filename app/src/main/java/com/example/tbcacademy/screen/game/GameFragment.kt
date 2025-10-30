package com.example.tbcacademy.screen.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tbcacademy.Cell
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentGameBinding
import com.example.tbcacademy.utils.extensions.showSnackBar

class GameFragment : BaseFragment<FragmentGameBinding>() {

    private var boardSize = 3
    private var currentPlayer = "X"
    private lateinit var cells: MutableList<Cell>
    private lateinit var adapter: BoardAdapter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGameBinding {
        return FragmentGameBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        boardSize = arguments?.getInt("boardSize") ?: 3

        cells = MutableList(boardSize * boardSize) { i ->
            Cell(row = i / boardSize, column = i % boardSize)
        }

        adapter = BoardAdapter(boardSize, cells) { cell ->
            cell.value = currentPlayer
            adapter.notifyItemChanged(cell.row * boardSize + cell.column)

            if (checkWin(cell.row, cell.column)) {
                binding.root.showSnackBar("Player $currentPlayer wins!")
                disableAllCells()
            } else if (isBoardFull()) {
                binding.root.showSnackBar("Draw")
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
            }
        }

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), boardSize)
        binding.recyclerView.adapter = adapter
    }

    private fun checkWin(row: Int, column: Int): Boolean {
        val player = cells[row * boardSize + column].value
        if ((0 until boardSize).all { cells[row * boardSize + it].value == player }) return true
        if ((0 until boardSize).all { cells[it * boardSize + column].value == player }) return true
        if (row == column && (0 until boardSize).all { cells[it * boardSize + it].value == player }) return true
        if (row + column == boardSize - 1 && (0 until boardSize).all { cells[it * boardSize + (boardSize - 1 - it)].value == player }) return true
        return false
    }

    private fun isBoardFull(): Boolean = cells.all { it.value.isNotEmpty() }

    private fun disableAllCells() {
        cells.forEach { it.isEnabled = false }
        adapter.notifyDataSetChanged()
    }
}
