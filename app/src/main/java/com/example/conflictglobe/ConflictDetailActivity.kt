package com.example.conflictglobe

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Screen that displays the details of a specific conflict
class ConflictDetailActivity : AppCompatActivity() {

    // Store the ID of the conflict we want to view
    private var id: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Get the conflict ID passed in from the previous screen
        id = intent.getLongExtra("id", -1)
    }

    override fun onResume() {
        super.onResume()

        // Load saved data to make sure details are up to date
        ConflictStore.load(this)

        // Find the matching conflict. close the screen if it doesn't exist
        val conflict = ConflictStore.byId(id) ?: run {
            finish()
            return
        }

        // Set screen title to the conflict name
        title = conflict.name

        // Fallback text if start date isnt given
        val startDateStr =
            conflict.startDate.ifBlank { getString(R.string.not_set) }

        // Fallback text if latitude or longitude are missing
        val latStr =
            conflict.latitude?.toString() ?: "—"

        val lonStr =
            conflict.longitude?.toString() ?: "—"

        // Format and display all the details inside the text view
        findViewById<TextView>(R.id.detailText).text =
            getString(
                R.string.detail_format,
                conflict.region,
                startDateStr,
                conflict.status,
                conflict.severity,
                latStr,
                lonStr,
                conflict.description
            )
    }
}