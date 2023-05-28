package com.methe.assignmentapp.view.note.add

import android.app.Application
import androidx.lifecycle.ViewModel
import com.methe.assignmentapp.model.Note
import com.methe.assignmentapp.repository.NoteRepository

class NoteAddUpdateViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun insert(note: Note) {
        mNoteRepository.insert(note)
    }

    fun update(note: Note) {
        mNoteRepository.update(note)
    }

    fun delete(note: Note) {
        mNoteRepository.delete(note)
    }
}