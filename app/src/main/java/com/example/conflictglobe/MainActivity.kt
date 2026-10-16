package com.example.conflictglobe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ConflictAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ConflictStore.load(this)

        adapter = ConflictAdapter(ConflictStore.conflicts) { }

        findViewById<RecyclerView>(R.id.conflictList).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    override fun onResume() {
        super.onResume()

        ConflictStore.load(this)

        if (::adapter.isInitialized) {
            adapter.update(ConflictStore.conflicts)
        }
    }
}