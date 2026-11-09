package com.example.conflictglobe

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

//handles loading and saving and finding conflict records conflict data
//manages save and load conflict data
object ConflictStore {

    //storage file name
    private const val FILE = "conflicts.json"

    //list of conflicts in memory
    var conflicts = mutableListOf<Conflict>()

    //reads conflicts from JSON file
    fun load(context: Context) {
        conflicts.clear()

        //read file content or return array empty if missing
        val text = try {
            context.openFileInput(FILE)
                .bufferedReader()
                .use { it.readText() }
        } catch (_: Exception) {
            "[]"
        }

        val arr = JSONArray(text)

        //json to conflict items
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)

            conflicts.add(
                Conflict(
                    id = o.getLong("id"),
                    name = o.getString("name"),
                    region = o.getString("region"),
                    startDate = o.getString("startDate"),
                    status = o.getString("status"),
                    severity = o.getString("severity"),
                    latitude = o.optDouble("latitude")
                        .takeUnless { it.isNaN() },
                    longitude = o.optDouble("longitude")
                        .takeUnless { it.isNaN() },
                    description = o.optString("description"),
                    imageUri = o.optString("imageUri").takeUnless { it.isBlank() || it == "null" }
                )
            )
        }
    }

    //saves current to JSON
    fun save(context: Context) {
        val arr = JSONArray()

        //convert conflict objects to JSON
        conflicts.forEach { c ->
            arr.put(
                JSONObject().apply {
                    put("id", c.id)
                    put("name", c.name)
                    put("region", c.region)
                    put("startDate", c.startDate)
                    put("status", c.status)
                    put("severity", c.severity)
                    put("latitude", c.latitude ?: JSONObject.NULL)
                    put("longitude", c.longitude ?: JSONObject.NULL)
                    put("description", c.description)
                    put("imageUri", c.imageUri ?: JSONObject.NULL)
                }
            )
        }

        //write json string to file
        context.openFileOutput(
            FILE,
            Context.MODE_PRIVATE
        ).bufferedWriter().use {
            it.write(arr.toString(2))
        }
    }

    // get one conflict by id
    fun byId(id: Long) = conflicts.find { it.id == id }

    fun create(conflict: Conflict) {
        conflict.validate()
        conflicts.add(conflict)
    }

    fun update(conflict: Conflict): Boolean {
        conflict.validate()
        val found = byId(conflict.id)
        return if (found != null) {
            found.name = conflict.name
            found.region = conflict.region
            found.startDate = conflict.startDate
            found.status = conflict.status
            found.severity = conflict.severity
            found.latitude = conflict.latitude
            found.longitude = conflict.longitude
            found.description = conflict.description
            found.imageUri = conflict.imageUri
            true
        } else {
            false
        }
    }
}
