package com.andrewvora.jaru.questionsets

import com.andrewvora.jaru.exceptions.BadRequestException
import com.andrewvora.jaru.exceptions.NotFoundException
import com.andrewvora.jaru.questions.QuestionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/content/v1")
class QuestionSetController
@Autowired
constructor(private val questionSetRepository: QuestionSetRepository,
			private val questionRepository: QuestionRepository,
			private val questionSetValidator: QuestionSetValidator) {

	@GetMapping("/set/{setId}")
	@ResponseBody
	fun get(@PathVariable("setId") setId: String?): QuestionSet {
		if (setId.isNullOrBlank()) {
			throw BadRequestException()
		}

		val result = questionSetRepository.findById(setId)
		return if (result.isPresent) {
			result.get()
		} else {
			throw NotFoundException()
		}
	}

	@PostMapping("/set")
	@ResponseBody
	fun post(@RequestBody questionSet: QuestionSet?): QuestionSet {
		if (questionSet == null || !questionSetValidator.canBeInserted(questionSet)) {
			throw BadRequestException()
		}

		return questionSetRepository.save(questionSet)
	}

	@PutMapping("/set/{setId}")
	@ResponseBody
	fun put(@PathVariable("setId") id: String?,
			@RequestBody questionSet: QuestionSet?): QuestionSet {

		val safeQuestionSet = if (questionSet?.setId.isNullOrBlank()) {
			questionSet?.copy(setId = id ?: "")
		} else {
			questionSet
		}

		if (safeQuestionSet == null || id.isNullOrBlank() || !questionSetValidator.canBeInserted(safeQuestionSet)) {
			throw BadRequestException()
		}

		return questionSetRepository.save(safeQuestionSet)
	}

	@DeleteMapping("/set/{setId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	fun delete(@PathVariable("setId") id: String?) {
		if (id.isNullOrBlank()) {
			throw BadRequestException()
		}

		questionSetRepository.deleteById(id)
	}

	@PutMapping("/set/{setId}/add/{questionId}")
	@ResponseBody
	fun addQuestion(@PathVariable("setId") setId: String?,
					@PathVariable("questionId") questionId: String?): QuestionSet {

		if (setId.isNullOrBlank() || questionId.isNullOrBlank()) {
			throw BadRequestException()
		}

		val matchingSet = questionSetRepository.findById(setId)
		val matchingQuestion = questionRepository.findById(questionId)
		return if (matchingSet.isPresent && matchingQuestion.isPresent) {
			val set = matchingSet.get()
			val question = matchingQuestion.get()

			set.questions.add(question)

			questionSetRepository.save(set)
		} else {
			throw NotFoundException()
		}
	}
}