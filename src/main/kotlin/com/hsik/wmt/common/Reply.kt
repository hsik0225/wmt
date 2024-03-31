package com.hsik.wmt.common

import com.fasterxml.jackson.annotation.JsonUnwrapped

open class Reply<T>() {
    @get:JsonUnwrapped
    var content: T? = null

    constructor(content: T) : this() {
        this.content = content
    }
}

fun <T> T.toReply(): Reply<T> = Reply(this)
