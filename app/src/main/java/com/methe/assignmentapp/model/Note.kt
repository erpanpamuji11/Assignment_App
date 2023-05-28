package com.methe.assignmentapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int = 0,

    @ColumnInfo("description")
    var description: String? = null,

    @ColumnInfo("date")
    var date: String? = null

): Parcelable