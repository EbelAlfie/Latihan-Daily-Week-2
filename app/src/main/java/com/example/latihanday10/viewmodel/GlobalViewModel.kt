package com.example.latihanday10.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.latihanday10.model.GatewayModel
import com.example.latihanday10.repository.DaerahRepository

class GlobalViewModel: ViewModel() {
    private var daerahList= MutableLiveData<GatewayModel>()
    private val repo = DaerahRepository()

    fun daerahListStatus(): LiveData<GatewayModel> = daerahList

    fun setList(type: Int, requiredId: Int) {
        getLists(type, requiredId)
    }

    private fun getLists(type: Int, requiredId: Int) {
        daerahList = repo.getRespon(type, requiredId)
    }
}