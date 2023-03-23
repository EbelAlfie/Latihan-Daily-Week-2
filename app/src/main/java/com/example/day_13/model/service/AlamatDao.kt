package com.example.day_13.model.service

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.day_13.model.AlamatModel

@Dao
interface AlamatDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAlamat(alamat: AlamatModel)

    @Delete
    fun deleteAlamat(alamat: AlamatModel)

    @Query("SELECT * FROM alamatdb")
    fun getAllAlamat(): LiveData<List<AlamatModel>>

    @Query("SELECT * FROM alamatdb WHERE id=:id")
    fun getSpesificAlamat(id: Int): LiveData<AlamatModel>

    @Update
    fun updateAlamat(alamat: AlamatModel)
}