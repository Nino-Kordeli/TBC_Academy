package com.example.tbcacademy.screen.register.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.R
import com.example.tbcacademy.screen.register.model.FieldItem

class FieldsAdapter(
    private val onValueChanged: (fieldId: Int, value: String) -> Unit
) : ListAdapter<FieldItem, FieldsAdapter.InputViewHolder>(DiffCallback()) {

    inner class InputViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val editText: EditText = view.findViewById(R.id.inputEditText)
        private var currentItem: FieldItem? = null

        init {
            editText.addTextChangedListener { editable ->
                currentItem?.let {
                    it.userValue = editable.toString()
                    onValueChanged(it.fieldId, it.userValue)
                }
            }
        }

        fun bind(item: FieldItem) {
            currentItem = item
            editText.hint = item.hint
            editText.inputType = when (item.keyboard?.lowercase()) {
                "number" -> android.text.InputType.TYPE_CLASS_NUMBER
                else -> android.text.InputType.TYPE_CLASS_TEXT
            }
            if (editText.text.toString() != item.userValue) {
                editText.setText(item.userValue)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InputViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.input_field_item, parent, false)
        return InputViewHolder(view)
    }

    override fun onBindViewHolder(holder: InputViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<FieldItem>() {
        override fun areItemsTheSame(oldItem: FieldItem, newItem: FieldItem) =
            oldItem.fieldId == newItem.fieldId

        override fun areContentsTheSame(oldItem: FieldItem, newItem: FieldItem) =
            oldItem == newItem
    }
}
