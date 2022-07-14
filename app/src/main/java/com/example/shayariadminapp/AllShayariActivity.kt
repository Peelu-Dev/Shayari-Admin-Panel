package com.example.shayariadminapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shayariadminapp.adapter.AllShayariAdapter
import com.example.shayariadminapp.databinding.ActivityAllShayariBinding
import com.example.shayariadminapp.model.ShayariModel
import com.google.firebase.firestore.FirebaseFirestore

class AllShayariActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityAllShayariBinding
    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllShayariBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        supportActionBar?.hide()

        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")

        binding.toolbarTitle.text = name.toString()

        db.collection("Shayari").document(id!!).collection("all")
            .addSnapshotListener{ value,error ->
                val shayariList = arrayListOf<ShayariModel>()
                val data = value?.toObjects(ShayariModel::class.java)
                shayariList.addAll(data!!)
                binding.rcvAllShayari.layoutManager = LinearLayoutManager(this)
                binding.rcvAllShayari.adapter = AllShayariAdapter(this,shayariList,id)

            }
    }
}