package com.example.latihanday8

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListAlamatFrag: Fragment(), AdapterAlamat.Utility {
    private lateinit var tvTambahAlamat: TextView
    private lateinit var recListAlamat: RecyclerView
    lateinit var adapterAlamat: AdapterAlamat
    lateinit var listOfAlamat: MutableList<Alamat>
    private lateinit var tvNoData: TextView

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
                    adapterAlamat.notifyItemChanged(position)
                    dataKosong()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_alamat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initInteraction()
    }

    private fun initInteraction() {
        tvTambahAlamat.setOnClickListener {
            val intent = Intent(activity, DetailAlamat::class.java)
            intent.putExtra("position", listOfAlamat.size + 1)
            startForResult.launch(intent)
        }
    }

    private fun initView(view: View) {
        tvTambahAlamat = view.findViewById(R.id.tv_tambahalamat)
        recListAlamat = view.findViewById(R.id.rec_listalamat)
        tvNoData = view.findViewById(R.id.no_data)
        recListAlamat.layoutManager = LinearLayoutManager(requireContext())

        listOfAlamat = mutableListOf()
        listOfAlamat.add(Alamat("bandung", "bisa", "Aku", "089643728123", true))
        listOfAlamat.add(Alamat("jakarta", "wefwd", "bima", "089643728123", false))
        listOfAlamat.add(Alamat("tanggerang", "sbdbdsvxxv", "mikhael", "089643728123", false))
        listOfAlamat.add(Alamat("bali", "pantai", "wefwfe", "089643728123", true))
        listOfAlamat.add(Alamat("wegwe", "xvcvcx", "wefwfe", "089643728123", false))
        listOfAlamat.add(Alamat("wetds", "vxcvx", "wefwfe", "089643728123", true))
        listOfAlamat.add(Alamat("sdbsdb", "vxccxv", "wefwfe", "089643728123", true))
        listOfAlamat.add(Alamat("bsdbd", "efwfe", "wefwfe", "089643728123", false))
        listOfAlamat.add(Alamat("sbdbds", "fewfew", "wefwfe", "089643728123", true))
        listOfAlamat.add(Alamat("sbdbds", "fewfew", "wefwfe", "089643728123", true))

        adapterAlamat = AdapterAlamat(listOfAlamat, this)

        adapterAlamat.listenerUtility = this
        recListAlamat.adapter = adapterAlamat
    }

    override fun onUbahItemListener(position: Int) {
        val intent = Intent(activity, DetailAlamat::class.java)
        intent.putExtra("DataAlamat", listOfAlamat[position])
        intent.putExtra("position", position)
        startForResult.launch(intent)
    }

    override fun onDeleteItemListener(position: Int) {
        listOfAlamat.removeAt(position)
        adapterAlamat.notifyItemRemoved(position)
        adapterAlamat.notifyItemRangeChanged(position, listOfAlamat.size)
        dataKosong()
    }

    private fun dataKosong() {
        tvNoData.visibility = if (listOfAlamat.isEmpty()) View.VISIBLE else View.GONE
    }
}