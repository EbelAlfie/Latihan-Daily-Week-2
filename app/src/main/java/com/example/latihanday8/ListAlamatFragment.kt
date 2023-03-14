package com.example.latihanday8

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanday8.databinding.ListAlamatBinding
import com.google.android.material.snackbar.Snackbar

class ListAlamatFragment: Fragment(), AdapterAlamat.Utility {
    private lateinit var binding: ListAlamatBinding

    lateinit var adapterAlamat: AdapterAlamat
    lateinit var listOfAlamat: MutableList<AlamatModel>

    private var alamat: AlamatModel? = null
    private var position: Int = 0

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 100 && result.data != null) {
                var message = getString(R.string.data_added)
                alamat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) result.data?.getParcelableExtra("DataAlamat", AlamatModel::class.java)
                else result.data?.getParcelableExtra("DataAlamat")

                position = result.data!!.getIntExtra("position", listOfAlamat.size + 1)
                if (position != listOfAlamat.size + 1) {
                    listOfAlamat[position] = alamat!!
                    message = getString(R.string.data_changed)
                }
                else listOfAlamat.add(alamat!!)
                adapterAlamat.notifyItemChanged(position)
                dataKosong()
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), getString(R.string.data_kosong), Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListAlamatBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecView()
        initInteraction()
    }

    private fun initInteraction() {
        binding.tvTambahAlamat.setOnClickListener {
            val intent = Intent(activity, DetailAlamatActivity::class.java)
            intent.putExtra("position", listOfAlamat.size + 1)
            startForResult.launch(intent)
        }
    }

    private fun initRecView() {
        binding.recListAlamat.layoutManager = LinearLayoutManager(requireContext())
        listOfAlamat = populateListAlamat()
        adapterAlamat = AdapterAlamat(listOfAlamat, this)
        binding.recListAlamat.adapter = adapterAlamat
    }

    private fun populateListAlamat(): MutableList<AlamatModel> {
        return mutableListOf(
            AlamatModel("bandung", "bisa", "Aku", "089643728123", true),
            AlamatModel("jakarta", "wefwd", "bima", "089643728123", false),
            AlamatModel("tanggerang", "sbdbdsvxxv", "mikhael", "089643728123", false),
            AlamatModel("bali", "pantai", "wefwfe", "089643728123", true),
            AlamatModel("wegwe", "xvcvcx", "wefwfe", "089643728123", false),
            AlamatModel("wetds", "vxcvx", "wefwfe", "089643728123", true)
        )
    }

    override fun onUbahItemListener(position: Int) {
        val intent = Intent(activity, DetailAlamatActivity::class.java)
        intent.putExtra("DataAlamat", listOfAlamat[position])
        intent.putExtra("position", position)
        startForResult.launch(intent)
    }

    override fun onDeleteItemListener(position: Int) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply{
            setTitle(getString(R.string.hapus_title))
            setMessage(getString(R.string.happus_confirm_msg))
            setIcon(activity?.let { ResourcesCompat.getDrawable(it.resources, R.drawable.baseline_warning_24, null) })
            setPositiveButton(getString(R.string.ya)) {_, _ ->
                hapusData(position)
            }
            setNegativeButton(getString(R.string.tidak)) {dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    private fun hapusData(position: Int) {
        listOfAlamat.removeAt(position)
        adapterAlamat.notifyItemRemoved(position)
        adapterAlamat.notifyItemRangeChanged(position, listOfAlamat.size)
        dataKosong()
        setSnackBar()
    }

    private fun dataKosong() {
        binding.tvNoData.visibility = if (listOfAlamat.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun setSnackBar() {
        Snackbar.make(binding.clForSnackbar, getString(R.string.data_deleted), Snackbar.LENGTH_SHORT).show()
    }
}