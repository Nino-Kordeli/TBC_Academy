package com.example.tbcacademy.screen.register.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.ItemFieldGroupBinding
import com.example.tbcacademy.screen.register.model.FieldItem

class FieldsAdapterGrouped(
    private val fields: List<FieldItem>
) : RecyclerView.Adapter<FieldsAdapterGrouped.GroupViewHolder>() {

    private val groupedFields = fields.chunked(3)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding = ItemFieldGroupBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GroupViewHolder(binding)
    }

    override fun getItemCount() = groupedFields.size

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(groupedFields[position])
    }

    inner class GroupViewHolder(private val binding: ItemFieldGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fields: List<FieldItem>) {
            binding.fieldContainer.removeAllViews()

            fields.forEach { field ->
                val fieldView = LayoutInflater.from(itemView.context)
                    .inflate(R.layout.input_field_item, binding.fieldContainer, false)
                val editText =
                    fieldView.findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.inputEditText)
                editText.hint = field.hint

                binding.fieldContainer.addView(fieldView)
            }
        }
    }
}