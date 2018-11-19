package com.andrewvora.jaru.questions

import com.andrewvora.jaru.exceptions.BadRequestException
import com.andrewvora.jaru.exceptions.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
@RequestMapping("/content/v1")
class QuestionController
@Autowired
constructor(private val questionRepository: QuestionRepository) {

	@GetMapping("/question/{questionId}")
	@ResponseBody
	fun get(@PathVariable("questionId") id: String?): Question {
		if (id == null) {
			throw BadRequestException()
		}

		val questionResult = questionRepository.findById(id)
		return if (questionResult.isPresent) {
			questionResult.get()
		} else {
			throw NotFoundException()
		}
	}

	@DeleteMapping("/question/{questionId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	fun delete(@PathVariable("questionId") id: String?) {
		if (id == null) {
			throw BadRequestException()
		}

		questionRepository.deleteById(id)
	}

	@PutMapping("/question/{questionId}")
	@ResponseBody
	fun put(@PathParam("questionId") id: String?,
			@RequestBody question: Question?): Question {

		if (id == null || question == null) {
			throw BadRequestException()
		}

		return questionRepository.save(question)
	}

	@PostMapping("/question")
	@ResponseBody
	fun post(@RequestBody question: Question?): Question {
		if (question == null) {
			throw BadRequestException()
		}

		return questionRepository.save(question)
	}
}