package com.arpansircar.notes_app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arpansircar.notes_app.common.DateTimeUtils
import com.arpansircar.notes_app.common.DateTimeUtils.convertMillisToDateTime
import com.arpansircar.notes_app.databinding.ItemNoteBinding
import com.arpansircar.notes_app.domain.models.Note

class HomeAdapter(
    private val notesList: List<Note>?
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        notesList?.get(position)?.let {
            holder.binding.tvTitle.text = it.noteTitle
            holder.binding.tvDetail.text = it.noteDetail
            holder.binding.tvDateCreated.text = convertMillisToDateTime(it.noteCreatedAt)
        }
    }

    override fun getItemCount(): Int = notesList?.size ?: 0
}