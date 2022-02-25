package com.example.contactuserside.db.repository

import androidx.lifecycle.LiveData
import com.example.contactuserside.db.dao.uidDao
import com.example.contactuserside.models.dataModel


class UidRepository(private val dao:uidDao) {

    val allUIDs: LiveData<List<dataModel>> = dao.getUIDItems()

    suspend fun insertUID(UIDItem: dataModel) = dao.insertUID(UIDItem)

    suspend fun deleteUID()=dao.emptyUIDs()
}