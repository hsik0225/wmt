package com.hsik.wmt.config

import io.swagger.v3.oas.annotations.media.Schema

data class GlobalErrorFormat(
    @Schema(description = "error occured time")
    val timestamp: String,
    @Schema(description = "exception message")
    val message: String,
    @Schema(description = "exception name")
    val type: String,
    @Schema(description = "cause by")
    val causeBy: Map<String, Any?>? = null,
    @Schema(description = "stack trace")
    val trace: String? = null,
)
