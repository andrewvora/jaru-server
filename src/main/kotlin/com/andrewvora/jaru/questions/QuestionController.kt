package com.andrewvora.jaru.questions

import com.andrewvora.jaru.answers.AnswerValidator
import com.andrewvora.jaru.exceptions.BadRequestException
import com.andrewvora.jaru.exceptions.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/content/v1")
class QuestionController
@Autowired
constructor(private val questionRepository: QuestionRepository,
			private val questionValidator: QuestionValidator,
			private val answerValidator: AnswerValidator) {

	@GetMapping("/question/{questionId}")
	@ResponseBody
	fun get(@PathVariable("questionId") id: String?): Question {
		if (id.isNullOrBlank()) {
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
		if (id.isNullOrBlank()) {
			throw BadRequestException()
		}

		questionRepository.deleteById(id)
	}

	@PutMapping("/question/{questionId}")
	@ResponseBody
	fun put(@PathVariable("questionId") id: String?,
			@RequestBody question: Question?): Question {

		// set ID on question if not provided
		val safeQuestion = if (question?.questionId == null) {
			question?.copy(questionId = id ?: "")
		} else {
			question
		}

		// validate parameters
		if (id.isNullOrBlank() || safeQuestion == null || !questionValidator.canBeInserted(safeQuestion)) {
			throw BadRequestException()
		}

		validateAnswers(safeQuestion)

		// save
		return questionRepository.save(safeQuestion)
	}

	@PostMapping("/question")
	@ResponseBody
	fun post(@RequestBody question: Question?): Question {
		if (question == null || !questionValidator.canBeInserted(question)) {
			throw BadRequestException()
		}

		validateAnswers(question)

		return questionRepository.save(question)
	}

	private fun validateAnswers(question: Question) {
		question.answers.forEach {
			if (!answerValidator.canBeInserted(it)) {
				throw BadRequestException()
			}
		}
	}
}