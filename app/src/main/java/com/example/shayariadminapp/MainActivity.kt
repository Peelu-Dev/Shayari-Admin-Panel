package com.example.shayariadminapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shayariadminapp.adapter.CategoryAdaptor
import com.example.shayariadminapp.databinding.ActivityMainBinding
import com.example.shayariadminapp.model.CategoryModel
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    // to bind the screen
    private lateinit var binding: ActivityMainBinding
    // to get data from FireBase
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        db = FirebaseFirestore.getInstance()

        db.collection("Shayari").addSnapshotListener{ value,error ->
            val shayari = arrayListOf<CategoryModel>()
            val data = value?.toObjects(CategoryModel::class.java)
            shayari.addAll(data!!)
            binding.recyclerViewCategory.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewCategory.adapter = CategoryAdaptor(this,shayari)
        }
    }
}