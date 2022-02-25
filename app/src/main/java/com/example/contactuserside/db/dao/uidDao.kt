package com.example.contactuserside.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contactuserside.models.dataModel

@Dao
interface uidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUID(uidItem: dataModel)

    @Query("SELECT * FROM dataModel ORDER BY id ASC")
    fun getUIDItems(): LiveData<List<dataModel>>

    @Query("DELETE FROM dataModel")
    suspend fun emptyUIDs()

}