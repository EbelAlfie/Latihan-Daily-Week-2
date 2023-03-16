package com.example.latihanday10.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.latihanday10.model.GatewayModel
import com.example.latihanday10.repository.DaerahRepository

class GlobalViewModel: ViewModel() {
    var daerahList= MutableLiveData<GatewayModel>()
    val repo = DaerahRepository()

    fun daerahListStatus(): LiveData<GatewayModel> = daerahList

    fun setList(type: Int, requiredId: Int) {
        getLists(type, requiredId)
    }

    fun getLists(type: Int, requiredId: Int) {
        daerahList = repo.getRespon(type, requiredId)
    }
}