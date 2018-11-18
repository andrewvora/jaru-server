package com.andrewvora.jaru.answers

import com.andrewvora.jaru.exceptions.BadRequestException
import com.andrewvora.jaru.exceptions.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
class AnswerController
@Autowired
constructor(private val answerRepository: AnswerRepository) {

	@GetMapping("/answer/{answerId}")
	@ResponseBody
	fun get(@PathVariable("answerId") answerId: String?): Answer {
		if (answerId == null) {
			throw BadRequestException()
		}

		val result = answerRepository.findById(answerId)
		return if (result.isPresent) {
			result.get()
		} else {
			throw NotFoundException()
		}
	}
}