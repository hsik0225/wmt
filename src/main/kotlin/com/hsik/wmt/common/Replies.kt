package com.hsik.wmt.common

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

open class Replies<T>(
    @JsonIgnore
    val data: Iterable<T>,
) {
    @JsonProperty("content")
    open fun getContent(): Collection<T> {
        val content = ArrayList<T>()
        data.toCollection(content)
        return content
    }
}

fun <T> List<T>.toReplies(): Replies<T> = Replies(this)
