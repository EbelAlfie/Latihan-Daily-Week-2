package com.example.latihanday7

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity: AppCompatActivity(), AdapterAlamat.Utility {
    private lateinit var tvTambahAlamat: TextView
    private lateinit var recListAlamat: RecyclerView
    lateinit var adapterAlamat: AdapterAlamat
    lateinit var listOfAlamat: MutableList<Alamat>

    private var alamat: Alamat? = null
    private var position: Int = 0

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 100 && result.data != null) {
                alamat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra("DataAlamat", Alamat::class.java)
                } else result.data?.getParcelableExtra("DataAlamat")
                position = result.data!!.getIntExtra("position", listOfAlamat.size + 1)
                if (position != listOfAlamat.size + 1) {
                    listOfAlamat[position] = alamat!!
                    adapterAlamat.notifyItemChanged(position)
                } else {
                    listOfAlamat.add(alamat!!)
                    adapterAlamat.notifyItemChanged(adapterAlamat.itemCount)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initInteraction()
    }

    private fun initInteraction() {
        tvTambahAlamat.setOnClickListener {
            val intent = Intent(this, DetailAlamat::class.java)
            intent.putExtra("position", listOfAlamat.size + 1)
            startForResult.launch(intent)
        }
    }

    private fun initView() {
        tvTambahAlamat = findViewById(R.id.tv_tambahalamat)
        recListAlamat = findViewById(R.id.rec_listalamat)
        recListAlamat.layoutManager = LinearLayoutManager(this)

        listOfAlamat = mutableListOf()
        listOfAlamat.add(Alamat("gggg", "gegef", "Aku", "089643728123", true))
        listOfAlamat.add(Alamat("kwekjowmd", "wefwd", "wefwfe", "089643728123", false))
        listOfAlamat.add(Alamat("adasd", "sbdbdsvxxv", "wefwfe", "089643728123", false))
        listOfAlamat.add(Alamat("zvxvzv", "wexvvxfwd", "wefwfe", "089643728123", true))
        listOfAlamat.add(Alamat("wegwe", "xvcvcx", "wefwfe", "089643728123", false))
        listOfAlamat.add(Alamat("wetds", "vxcvx", "wefwfe", "089643728123", true))
        listOfAlamat.add(Alamat("sdbsdb", "vxccxv", "wefwfe", "089643728123", true))
        listOfAlamat.add(Alamat("bsdbd", "efwfe", "wefwfe", "089643728123", false))
        listOfAlamat.add(Alamat("sbdbds", "fewfew", "wefwfe", "089643728123", true))

        adapterAlamat = AdapterAlamat(listOfAlamat, this)

        adapterAlamat.listenerUtility = this
        recListAlamat.adapter = adapterAlamat
    }

    override fun onUbahItemListener(position: Int) {
        val intent = Intent(this, DetailAlamat::class.java)
        intent.putExtra("DataAlamat", listOfAlamat[position])
        intent.putExtra("position", position)
        startForResult.launch(intent)
    }

    override fun onDeleteItemListener(position: Int) {
        listOfAlamat.removeAt(position)
        adapterAlamat.notifyItemRemoved(position)
        adapterAlamat.notifyItemRangeChanged(position, listOfAlamat.size)
    }
}