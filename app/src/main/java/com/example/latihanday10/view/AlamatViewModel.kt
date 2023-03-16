package com.example.latihanday10.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.latihanday10.model.GatewayModel
import com.example.latihanday10.repository.DaerahRepository

class AlamatViewModel() : ViewModel(){
    private var dataDaerah = MutableLiveData<GatewayModel>()
    val repo = DaerahRepository()

    init {
        getData(0, 0)
    }

    fun getSavedData(): LiveData<GatewayModel> = dataDaerah

    private fun getData(type: Int, requiredId: Int) {
        dataDaerah = repo.getRespon(type, requiredId) as MutableLiveData<GatewayModel>
    }
}