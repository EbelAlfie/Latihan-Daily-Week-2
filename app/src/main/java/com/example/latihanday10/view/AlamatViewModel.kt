package com.example.latihanday10.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.latihanday10.model.AlamatModel
import com.example.latihanday10.model.GatewayModel
import com.example.latihanday10.repository.DaerahRepository

class AlamatViewModel() : ViewModel(){
    private var dataDaerah = MutableLiveData<GatewayModel>()
    private var dataAlamat = MutableLiveData<AlamatModel>()

    val repo = DaerahRepository()

    fun getSavedData(): LiveData<GatewayModel> = dataDaerah
    fun getAlamat(): LiveData<AlamatModel>? = dataAlamat

    fun setId(type: Int, requiredId: Int) {
        getData(type, requiredId)
    }
    private fun getData(type: Int, requiredId: Int) {
        dataDaerah = repo.getRespon(type, requiredId)
    }

    fun setNama(strProvinsi: String?, strKota: String?, strKecamatan: String?, strKelurahan: String?) {
        dataAlamat.value?.namaProvinsi = strProvinsi
        dataAlamat.value?.namaKota = strKota
        dataAlamat.value?.namaKecamatan = strKecamatan
        dataAlamat.value?.namaKelurahan = strKelurahan
    }
}