package com.andrewvora.jaru

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso

@SpringBootApplication
@EnableOAuth2Sso
class JaruApplication

fun main(args: Array<String>) {
	SpringApplication.run(JaruApplication::class.java, *args)
}