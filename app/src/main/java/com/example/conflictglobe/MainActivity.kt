package com.example.conflictglobe

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// main screen to show list of conflicts
class MainActivity : AppCompatActivity() {

    // adapter for managing recyclerview list
    private lateinit var adapter: ConflictAdapter
    private lateinit var search: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search = findViewById(R.id.searchInput)

        // load saved data into memory
        ConflictStore.load(this)

        // setup list adapter
        adapter = ConflictAdapter(ConflictStore.conflicts) { conflict ->
            startActivity(
                Intent(this, ConflictDetailActivity::class.java)
                    .putExtra("id", conflict.id)
            )
        }

        // configure RecyclerView display
        findViewById<RecyclerView>(R.id.conflictList).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        // handle text changes in the search input
        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                refresh()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        findViewById<View>(R.id.addButton).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AddEditConflictActivity::class.java
                )
            )
        }
    }

    // refresh data when you come back to this screen
    override fun onResume() {
        super.onResume()

        ConflictStore.load(this)

        // update the list if the adapter is ready
        if (::adapter.isInitialized) {
            refresh()
        }
    }

    // filters the conflicts list based on current search text
    private fun refresh() {
        val q = search.text.toString().trim()

        val filtered = ConflictStore.conflicts.filter { item ->
            q.isBlank() ||
                    item.name.contains(q, ignoreCase = true) ||
                    item.region.contains(q, ignoreCase = true)
        }

        adapter.update(filtered)
    }
}