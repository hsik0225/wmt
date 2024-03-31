package com.hsik.wmt.config

/**
 * Human Exception
 */
open class HumanException(val error: ErrorSource) : RuntimeException(error.message)

class DataNotFoundException(
    message: String,
    causeBy: Map<String, Any?>? = null,
) : HumanException(
    ErrorSource(message, causeBy),
)
