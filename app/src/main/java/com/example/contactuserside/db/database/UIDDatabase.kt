package com.example.contactuserside.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactuserside.db.dao.uidDao
import com.example.contactuserside.models.dataModel



@Database(entities = [dataModel::class], version = 1, exportSchema = false)
abstract class UIDDatabase:RoomDatabase() {
    abstract fun getDao(): uidDao

    companion object {

        @Volatile
        private var INSTANCE: UIDDatabase? = null

        fun getDatabase(context: Context): UIDDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UIDDatabase::class.java,
                    "uid_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}