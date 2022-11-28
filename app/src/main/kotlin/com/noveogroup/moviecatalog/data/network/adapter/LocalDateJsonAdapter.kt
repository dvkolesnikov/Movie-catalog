package com.noveogroup.moviecatalog.data.network.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class LocalDateJsonAdapter : JsonAdapter<LocalDate>() {

    private val dateFormatter: DateTimeFormatter by lazy {
        DateTimeFormatter.ofPattern(DATE_PATTERN)
    }

    @FromJson
    override fun fromJson(json: JsonReader): LocalDate? {
        val stringDate = json.nextString()
        return try {
            LocalDate.parse(stringDate, dateFormatter)
        } catch (ex: DateTimeParseException) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: LocalDate?) {
        writer.value(value?.format(dateFormatter))
    }

    companion object {
        private const val DATE_PATTERN = "yyyy-MM-dd"
    }
}
