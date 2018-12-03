package com.andrewvora.jaru.questions

import org.springframework.stereotype.Component

@Component
class QuestionValidator {

	fun isValid(question: Question): Boolean {
		val hasCorrectAnswer = question.correctAnswerId.isNullOrBlank().not()
		if (hasCorrectAnswer) {
			question.answers.firstOrNull {
				it.answerId == question.correctAnswerId
			} ?: return false
		}

		return question.textResName.isNotEmpty() && question.transcriptionResName.isNotEmpty() &&
				question.questionId.isNotEmpty()
	}
}