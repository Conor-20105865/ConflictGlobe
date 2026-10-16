package com.example.conflictglobe

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// main screen to show list of conflicts
class MainActivity : AppCompatActivity() {

    // adapter for managing recyclerview list
    private lateinit var adapter: ConflictAdapter
    private lateinit var search: EditText
    private lateinit var filter: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search = findViewById(R.id.searchInput)
        filter = findViewById(R.id.statusFilter)

        val filterOptions = listOf(
            getString(R.string.all_statuses),
            getString(R.string.status_active),
            getString(R.string.status_ceasefire),
            getString(R.string.status_ended)
        )

        filter.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            filterOptions
        )

        filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                refresh()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

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

        val selectedStatus =
            filter.selectedItem?.toString()
                ?: getString(R.string.all_statuses)

        val allStatuses = getString(R.string.all_statuses)

        val filtered = ConflictStore.conflicts.filter { item ->

            val matchesQuery =
                q.isBlank() ||
                        item.name.contains(q, ignoreCase = true) ||
                        item.region.contains(q, ignoreCase = true)

            val matchesStatus =
                selectedStatus == allStatuses ||
                        item.status == selectedStatus

            matchesQuery && matchesStatus
        }

        adapter.update(filtered)
    }
}