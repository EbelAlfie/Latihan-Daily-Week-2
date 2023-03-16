package com.example.latihanday10.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.latihanday10.model.GatewayModel
import com.example.latihanday10.repository.DaerahRepository

class AlamatViewModel : ViewModel(){
    val dataDaerah: MutableLiveData<GatewayModel>? = null

    fun getDataDaerah(type: Int, requiredId: Int): LiveData<GatewayModel> {
        return DaerahRepository.getRespon(type, requiredId)
    }
}