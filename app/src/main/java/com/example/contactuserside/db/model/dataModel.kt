package com.example.contactuserside.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class dataModel(
    val uid: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}