package com.methe.mynotesapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.methe.assignmentapp.model.Note

class NoteDiffCallback(private val mOldNoteList: List<Note>, private val mNewNoteList: List<Note>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldNoteList.size
    }
    override fun getNewListSize(): Int {
        return mNewNoteList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldNoteList[oldItemPosition].id == mNewNoteList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldNoteList[oldItemPosition]
        val newEmployee = mNewNoteList[newItemPosition]
        return oldEmployee.date == newEmployee.date && oldEmployee.description == newEmployee.description
    }
}