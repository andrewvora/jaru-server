package com.andrewvora.jaru

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class JaruApplication

fun main(args: Array<String>) {
	SpringApplication.run(JaruApplication::class.java, *args)
}