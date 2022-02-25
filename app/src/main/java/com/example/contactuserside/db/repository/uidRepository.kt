package com.example.contactuserside.db.repository

import androidx.lifecycle.LiveData
import com.example.contactuserside.db.dao.uidDao
import com.example.contactuserside.db.model.dataModel


class uidRepository(private val dao:uidDao) {

    val allUIDs: LiveData<List<dataModel>> = dao.getUIDItems()

    suspend fun insertUID(UIDItem: dataModel) = dao.insertUID(UIDItem)

    suspend fun deleteUID()=dao.emptyUIDs()
}