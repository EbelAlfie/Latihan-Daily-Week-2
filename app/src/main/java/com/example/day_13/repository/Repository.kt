package com.example.day_13.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.day_13.model.AlamatModel
import com.example.day_13.model.service.AlamatDao
import com.example.day_13.model.service.AlamatDb
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(context: Context) {
    private val alamatDaoApi: AlamatDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = AlamatDb.getDatabase(context)
        alamatDaoApi = db.alamatDao()
    }

    fun getAllNotes(): LiveData<List<AlamatModel>> = alamatDaoApi.getAllAlamat()

    fun insert(alamat: AlamatModel) {
        executorService.execute { alamatDaoApi.insertAlamat(alamat) }
    }

    fun deleteAlamat(alamat: AlamatModel) {
        executorService.execute { alamatDaoApi.deleteAlamat(alamat) }
    }

    fun updateAlamat(alamat: AlamatModel) {
        executorService.execute { alamatDaoApi.updateAlamat(alamat) }
    }

    fun getSpesificAlamat(id: Int): LiveData<AlamatModel> = alamatDaoApi.getSpesificAlamat(id)
}