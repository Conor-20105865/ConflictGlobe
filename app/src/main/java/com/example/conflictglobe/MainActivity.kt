package com.example.conflictglobe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.view.View

//main screen to show list of conflicts
class MainActivity : AppCompatActivity() {

    //adapter for managing recyclerview list
    private lateinit var adapter: ConflictAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //load saved data into memory
        ConflictStore.load(this)

        //setup list adapter
        adapter = ConflictAdapter(ConflictStore.conflicts) { }

        //configure RecylerView display
        findViewById<RecyclerView>(R.id.conflictList).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        findViewById<View>(R.id.addButton).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AddEditConflictActivity::class.java
                )
            )
        }
    }

    //refresh data when you come back to this screen
    override fun onResume() {
        super.onResume()

        ConflictStore.load(this)

        //update tje list if the adapter is ready
        if (::adapter.isInitialized) {
            adapter.update(ConflictStore.conflicts)
        }
    }
}