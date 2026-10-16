package com.example.conflictglobe

data class Conflict(
    val id: Long = System.currentTimeMillis(),
    var name: String,
    var region: String,
    var startDate: String,
    var status: String,
    var severity: String,
    var latitude: Double?,
    var longitude: Double?,
    var description: String
) {
    fun isValid(): Boolean {
        val lat = latitude
        val lon = longitude
        return name.isNotBlank() && region.isNotBlank() &&
                (lat == null || lat in -90.0..90.0) &&
                (lon == null || lon in -180.0..180.0)
    }
}
