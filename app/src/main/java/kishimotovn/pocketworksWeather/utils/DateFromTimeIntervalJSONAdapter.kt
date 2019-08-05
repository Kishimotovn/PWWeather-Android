package kishimotovn.pocketworksWeather.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.lang.Exception
import java.util.*

class DateFromTimeIntervalJSONAdapter: JsonAdapter<Date>() {
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val timeInterval = reader.nextLong()
            Date(timeInterval*1000)
        } catch(e: Exception) {
            null
        }
    }

    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            writer.value(value.time/1000)
        }
    }
}