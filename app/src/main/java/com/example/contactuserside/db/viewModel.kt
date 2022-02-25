package com.example.contactuserside.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.contactuserside.db.database.UIDDatabase
import com.example.contactuserside.db.model.dataModel
import com.example.contactuserside.db.repository.uidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class viewModel(application:Application): AndroidViewModel(application) {

    val allUidItems: LiveData<List<dataModel>>

    private val repository: uidRepository

    init {
        val dao = UIDDatabase.getDatabase(application).getDao()
        repository = uidRepository(dao)
        allUidItems = repository.allUIDs
    }

    fun insertUID(uidItem: dataModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertUID(uidItem)
    }

    fun deleteRepo() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteUID()
    }
}