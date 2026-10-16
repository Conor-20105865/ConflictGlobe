package com.example.conflictglobe

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.Locale

//screen for adding and editing a conflict entry
class AddEditConflictActivity : AppCompatActivity() {

    private var id: Long = -1
    private var date = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = intent.getLongExtra("id", -1)

        //find input fields
        val name = findViewById<EditText>(R.id.nameInput)
        val region = findViewById<EditText>(R.id.regionInput)
        val lat = findViewById<EditText>(R.id.latInput)
        val lon = findViewById<EditText>(R.id.lonInput)
        val desc = findViewById<EditText>(R.id.descriptionInput)

        //find dropdown spinners
        val status = findViewById<Spinner>(R.id.statusSpinner)
        val severity = findViewById<Spinner>(R.id.severitySpinner)
        val dateBtn = findViewById<Button>(R.id.dateButton)

        //populate status dropdown options
        val statuses = listOf(
            getString(R.string.status_active),
            getString(R.string.status_ceasefire),
            getString(R.string.status_ended)
        )

        status.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            statuses
        )

        //populate severity dropdown options
        val severities = listOf(
            getString(R.string.severity_low),
            getString(R.string.severity_medium),
            getString(R.string.severity_high),
            getString(R.string.severity_critical)
        )

        severity.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            severities
        )

        // Load existing conflict data if we are editing
        ConflictStore.load(this)

        ConflictStore.byId(id)?.let { c ->
            name.setText(c.name)
            region.setText(c.region)
            date = c.startDate
            if (c.startDate.isNotBlank()) {
                dateBtn.text = c.startDate
            }

            status.setSelection(statuses.indexOf(c.status))
            severity.setSelection(severities.indexOf(c.severity))

            lat.setText(c.latitude?.toString() ?: "")
            lon.setText(c.longitude?.toString() ?: "")
            desc.setText(c.description)
        }

        dateBtn.setOnClickListener {
            val c = Calendar.getInstance()

            DatePickerDialog(
                this,
                { _, year, month, day ->
                    date = String.format(
                        Locale.US,
                        "%02d/%02d/%04d",
                        day,
                        month + 1,
                        year
                    )

                    dateBtn.text = date
                },
                c[Calendar.YEAR],
                c[Calendar.MONTH],
                c[Calendar.DAY_OF_MONTH]
            ).show()
        }

        //handle clicky on save button
        findViewById<Button>(R.id.saveButton).setOnClickListener {
            //make conflict object from form inputs
            val conflict = Conflict(
                id = if (id == -1L) System.currentTimeMillis() else id,
                name = name.text.toString().trim(),
                region = region.text.toString().trim(),
                startDate = date,
                status = status.selectedItem.toString(),
                severity = severity.selectedItem.toString(),
                latitude = lat.text.toString().toDoubleOrNull(),
                longitude = lon.text.toString().toDoubleOrNull(),
                description = desc.text.toString().trim()
            )

            //validate before actually saving
            if (!conflict.isValid()) {
                Toast.makeText(
                    this,
                    getString(R.string.enter_valid_details_toast),
                    Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            }

            //update existing entry or add as new
            val index = ConflictStore.conflicts.indexOfFirst {
                it.id == conflict.id
            }

            if (index >= 0) {
                ConflictStore.conflicts[index] = conflict
            } else {
                ConflictStore.conflicts.add(conflict)
            }

            ConflictStore.save(this)

            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}