package com.example.conflictglobe

//conflict data model. and validation rules
data class Conflict(
    val id: Long = System.currentTimeMillis(),
    var name: String,
    var region: String,
    var startDate: String,
    var status: String,
    var severity: String,
    var latitude: Double?,
    var longitude: Double?,
    var description: String,
    var imageUri: String? = null
) {
    fun isValid(): Boolean {
        val lat = latitude
        val lon = longitude
        return name.isNotBlank() && region.isNotBlank() &&
                (lat == null || (lat.isFinite() && lat in -90.0..90.0)) &&
                (lon == null || (lon.isFinite() && lon in -180.0..180.0))
    }

    fun validate() {
        require(name.isNotBlank()) {
            "Name must not be empty."
        }
        require(region.isNotBlank()) {
            "Region must not be empty."
        }
        val lat = latitude
        if (lat != null) {
            require(lat.isFinite() && lat in -90.0..90.0) {
                "Latitude must be between -90 and 90."
            }
        }
        val lon = longitude
        if (lon != null) {
            require(lon.isFinite() && lon in -180.0..180.0) {
                "Longitude must be between -180 and 180."
            }
        }
    }
}
