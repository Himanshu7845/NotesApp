package com.unorg.notesapp.adapter

import android.content.Context
import android.graphics.Color
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.unorg.notesapp.databinding.ColorSinglerowBinding
import com.unorg.notesapp.views.NotesActivity

class ColorsAdapter(val context: Context, data1: Int, val data: (String) -> Unit) :
    ListAdapter<String, ColorsAdapter.MyViewHolder>(MyColorDiffUtil()) {
    var rowIndex: Int = data1

    inner class MyViewHolder(val binding: ColorSinglerowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: String) {
            val colorString = Color.parseColor(result.toString())
            binding.materialCardView.setCardBackgroundColor(colorString)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ColorSinglerowBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
        holder.binding.materialCardView.setOnClickListener {
            data.invoke(result)
            NotesActivity.setColor=null
            rowIndex = position
            notifyDataSetChanged()
        }
        if (rowIndex == position || NotesActivity.setColor!=null && NotesActivity.setColor==result) {
            holder.binding.materialCardView.strokeWidth = 4
            holder.binding.materialCardView.strokeColor = Color.parseColor("#2D2D2D")
        }

        else {
            holder.binding.materialCardView.strokeWidth = 0
            holder.binding.materialCardView.strokeColor = Color.parseColor("#000000")
        }
    }
}

class MyColorDiffUtil : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}