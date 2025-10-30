package com.example.tbcacademy.screen.game

import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.Cell
import com.example.tbcacademy.R

class BoardAdapter(
    private val boardSize: Int,
    private val cells: List<Cell>,
    private val onCellClicked: (cell: Cell) -> Unit
) : RecyclerView.Adapter<BoardAdapter.CellViewHolder>() {

    inner class CellViewHolder(val button: ImageButton) : RecyclerView.ViewHolder(button)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val button = ImageButton(parent.context).apply {
            setBackgroundResource(R.drawable.button_background)
            scaleType = ImageView.ScaleType.FIT_CENTER
            setPadding(16, 16, 16, 16)
            elevation = 4f
        }

        val size = parent.measuredWidth / boardSize
        button.layoutParams = RecyclerView.LayoutParams(size, size)

        return CellViewHolder(button)
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        val cell = cells[position]

        holder.button.setImageResource(
            when (cell.value) {
                "X" -> R.drawable.ic_x
                "O" -> R.drawable.ic_circle
                else -> 0
            }
        )

        holder.button.isEnabled = cell.value.isEmpty() && cell.isEnabled

        holder.button.setOnClickListener {
            if (cell.value.isEmpty() && cell.isEnabled) {
                onCellClicked(cell)
            }
        }
    }

    override fun getItemCount() = cells.size
}
