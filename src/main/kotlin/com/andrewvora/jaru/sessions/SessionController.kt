package com.andrewvora.jaru.sessions

import com.andrewvora.jaru.exceptions.BadRequestException
import com.andrewvora.jaru.exceptions.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
class SessionController
@Autowired
constructor(private val sessionRepository: SessionRepository) {

	@GetMapping("/session/{sessionId}")
	@ResponseBody
	fun get(@PathVariable("sessionId") sessionId: String?): Session {
		val result = sessionId?.let { id ->
			return@let sessionRepository.findById(id)
		}

		return if (result?.isPresent == true) {
			result.get()
		} else {
			throw NotFoundException()
		}
	}

	@PostMapping("/session")
	@ResponseBody
	fun post(@RequestBody session: Session?): Session {
		if (session == null) {
			throw BadRequestException()
		}

		val sequenceResult = sessionRepository.currentSequenceId()
		val nextSequenceId = if (sequenceResult.isPresent) {
			sequenceResult.get() + 1
		} else {
			0
		}
		val sanitizedSession = session.copy(sequenceId = nextSequenceId)
		return sessionRepository.save(sanitizedSession)
	}

	@DeleteMapping("/session/{sessionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	fun delete(@PathVariable("sessionId") sessionId: String?) {
		if (sessionId == null) {
			throw BadRequestException()
		}

		sessionRepository.deleteById(sessionId)
	}

	@GetMapping("/session/recent")
	@ResponseBody
	fun getRecent(): List<Session> {
		val result = sessionRepository.retrieveRecentSessions()

		return if (result.isPresent) {
			result.get()
		} else {
			emptyList()
		}
	}
}