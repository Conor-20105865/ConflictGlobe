package com.example.conflictglobe

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GlobeStatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_globe_stats)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.globe_stats_title)

        ConflictStore.load(this)

        val total = ConflictStore.conflicts.size
        val active = ConflictStore.conflicts.count { it.status.equals("Active", ignoreCase = true) }
        val ceasefire = ConflictStore.conflicts.count { it.status.equals("Ceasefire", ignoreCase = true) }
        val ended = ConflictStore.conflicts.count { it.status.equals("Ended", ignoreCase = true) }

        findViewById<TextView>(R.id.totalText).text = getString(R.string.total_conflicts_label, total)
        findViewById<TextView>(R.id.activeText).text = "Active Conflicts: $active"
        findViewById<TextView>(R.id.ceasefireText).text = "Ceasefire Conflicts: $ceasefire"
        findViewById<TextView>(R.id.endedText).text = "Ended Conflicts: $ended"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
