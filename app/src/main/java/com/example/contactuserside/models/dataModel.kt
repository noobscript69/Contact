package com.example.contactuserside.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class dataModel(
    val uid: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}