package com.unorg.notesapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.unorg.notesapp.databinding.NotesSinglerowBinding
import com.unorg.notesapp.roomdatabase.NoteTable

class NotesAdapter(val context: Context,val data:(NoteTable)->Unit,val notesData:(NoteTable)->Unit) :
    ListAdapter<NoteTable, NotesAdapter.MyViewHolder>(MyDiffUtil()) {
    inner class MyViewHolder(val binding: NotesSinglerowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: NoteTable) {
            binding.setData = result
            if(!result.color.isNullOrEmpty()){
                val colorString = Color.parseColor(result.color)
                binding.mainLayout.setCardBackgroundColor(colorString)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = NotesSinglerowBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
        holder.binding.delete.setOnClickListener {
            data(result)
        }
        holder.binding.mainLayout.setOnClickListener {
            notesData(result)
        }

    }
}

class MyDiffUtil : DiffUtil.ItemCallback<NoteTable>() {
    override fun areItemsTheSame(oldItem: NoteTable, newItem: NoteTable): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteTable, newItem: NoteTable): Boolean {
        return oldItem == newItem
    }
}