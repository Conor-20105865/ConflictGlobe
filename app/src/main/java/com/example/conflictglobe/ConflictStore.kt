package com.example.conflictglobe

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

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
                    description = o.optString("description")
                )
            )
        }
    }

    //saves current to JSON
    fun save(context: Context) {
        val arr = JSONArray()

        //convert conflict objects to JSOn
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
}