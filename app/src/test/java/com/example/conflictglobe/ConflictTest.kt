package com.example.conflictglobe

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ConflictTest {

    @Test
    fun validConflict_isValid() {
        val conflict = Conflict(
            name = "Example Conflict",
            region = "Example Region",
            startDate = "16/10/2026",
            status = "Active",
            severity = "High",
            latitude = 53.3,
            longitude = -6.2,
            description = "Example description"
        )

        assertTrue(conflict.isValid())
    }

    @Test
    fun latitudeAbove90_isInvalid() {
        val conflict = createConflict(
            latitude = 91.0
        )

        assertFalse(conflict.isValid())
    }

    @Test
    fun latitudeBelowMinus90_isInvalid() {
        val conflict = createConflict(
            latitude = -91.0
        )

        assertFalse(conflict.isValid())
    }

    @Test
    fun longitudeAbove180_isInvalid() {
        val conflict = createConflict(
            longitude = 181.0
        )

        assertFalse(conflict.isValid())
    }

    @Test
    fun longitudeBelowMinus180_isInvalid() {
        val conflict = createConflict(
            longitude = -181.0
        )

        assertFalse(conflict.isValid())
    }

    @Test
    fun blankName_isInvalid() {
        val conflict = createConflict(
            name = ""
        )

        assertFalse(conflict.isValid())
    }

    @Test
    fun blankRegion_isInvalid() {
        val conflict = createConflict(
            region = ""
        )

        assertFalse(conflict.isValid())
    }

    @Test
    fun latitudeBoundaryValues_areValid() {
        assertTrue(
            createConflict(latitude = 90.0).isValid()
        )

        assertTrue(
            createConflict(latitude = -90.0).isValid()
        )
    }

    @Test
    fun longitudeBoundaryValues_areValid() {
        assertTrue(
            createConflict(longitude = 180.0).isValid()
        )

        assertTrue(
            createConflict(longitude = -180.0).isValid()
        )
    }

    private fun createConflict(
        name: String = "Example Conflict",
        region: String = "Example Region",
        latitude: Double? = 53.3,
        longitude: Double? = -6.2
    ): Conflict {
        return Conflict(
            name = name,
            region = region,
            startDate = "16/10/2026",
            status = "Active",
            severity = "Medium",
            latitude = latitude,
            longitude = longitude,
            description = "Test conflict"
        )
    }
}