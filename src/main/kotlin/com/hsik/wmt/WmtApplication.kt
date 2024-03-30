package com.hsik.wmt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class WmtApplication

fun main(args: Array<String>) {
    runApplication<WmtApplication>(*args)
}
