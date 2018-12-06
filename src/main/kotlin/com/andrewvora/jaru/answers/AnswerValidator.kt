package com.andrewvora.jaru.answers

import org.springframework.stereotype.Component

@Component
class AnswerValidator {

	fun canBeInserted(answer: Answer): Boolean {
		return answer.answerId.isNotEmpty() && answer.textResName.isNotEmpty()
	}
}