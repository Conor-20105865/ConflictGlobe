package com.example.conflictglobe

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

//screen for adding and editing a conflict entry
class AddEditConflictActivity : AppCompatActivity() {

    private var date = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        //find input fields
        val name = findViewById<EditText>(R.id.nameInput)
        val region = findViewById<EditText>(R.id.regionInput)
        val lat = findViewById<EditText>(R.id.latInput)
        val lon = findViewById<EditText>(R.id.lonInput)
        val desc = findViewById<EditText>(R.id.descriptionInput)
        //find dropdown spinners
        val status = findViewById<Spinner>(R.id.statusSpinner)
        val severity = findViewById<Spinner>(R.id.severitySpinner)
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
        //handle clicky on save button
        findViewById<Button>(R.id.saveButton).setOnClickListener {
            //make conflict object from form inputs
            val conflict = Conflict(
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

            //save the conflict and close this screen
            ConflictStore.conflicts.add(conflict)
            ConflictStore.save(this)

            finish()
        }
    }
}