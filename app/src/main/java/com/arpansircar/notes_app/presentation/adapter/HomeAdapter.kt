package com.arpansircar.notes_app.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arpansircar.notes_app.common.utils.DateTimeUtils.convertMillisToDateTime
import com.arpansircar.notes_app.databinding.ItemNoteBinding
import com.arpansircar.notes_app.domain.models.Note

class HomeAdapter(
    private val notesList: List<Note>?,
    private val notePressedListener: NotePressedListener
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    interface NotePressedListener {
        fun onNotePressed(note: Note, view: View, position: Int)
    }

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
        notesList?.get(position)?.let { note ->
            holder.binding.tvTitle.text = note.noteTitle
            holder.binding.tvDetail.text = note.noteDetail
            holder.binding.tvDateCreated.text = convertMillisToDateTime(note.noteCreatedAt)

            holder.binding.llRoot.setOnLongClickListener {
                notePressedListener.onNotePressed(note, it, position)
                true
            }
        }
    }

    override fun getItemCount(): Int = notesList?.size ?: 0
}