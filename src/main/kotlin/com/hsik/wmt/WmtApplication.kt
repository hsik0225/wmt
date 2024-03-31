package com.hsik.wmt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WmtApplication

fun main(args: Array<String>) {
    runApplication<WmtApplication>(*args)
}
