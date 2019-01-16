package com.andrewvora.jaru.tokencheck

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 * Simple dummy endpoint to check that credentials are correct.
 */
@RestController
@RequestMapping("/content/v1")
class CredentialCheckController {

	@GetMapping("/check")
	@ResponseBody
	fun check(): ResponseEntity<String> {
		return ResponseEntity.ok("")
	}
}