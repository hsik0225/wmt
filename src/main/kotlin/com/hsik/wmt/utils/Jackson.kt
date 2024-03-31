package com.hsik.wmt.utils

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

object Jackson {
    private val dateTimePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    private val timePattern = DateTimeFormatter.ofPattern("HH:mm:ss")

    private val mapper = Jackson2ObjectMapperBuilder.json()
        .featuresToEnable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .modules(JavaTimeModule())
        .serializerByType(LocalDateTime::class.java, LocalDateTimeSerializer(dateTimePattern))
        .serializerByType(LocalTime::class.java, LocalTimeSerializer(timePattern))
        .serializers(CustomDateSerializer())
        .build<ObjectMapper>()
        .registerKotlinModule()

    fun getMapper(): ObjectMapper = mapper
}

private class CustomDateSerializer : StdSerializer<Date>(Date::class.java) {
    override fun serialize(value: Date?, gen: JsonGenerator?, provider: SerializerProvider?) {
        if (value != null && gen != null) {
            gen.writeString(
                LocalDateTime.ofInstant(
                    value.toInstant(),
                    ZoneId.systemDefault(),
                ).format(DateTimeFormatter.ISO_DATE_TIME),
            )
        }
    }
}

fun <T> T.toJson(): String = Jackson.getMapper().writeValueAsString(this)
inline fun <reified T> String.fromJson(): T = Jackson.getMapper().readValue(this)
