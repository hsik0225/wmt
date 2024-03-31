package com.hsik.wmt.config

class ErrorSource(
    val message: String,
    var causeBy: Map<String, Any?>? = null,
)
